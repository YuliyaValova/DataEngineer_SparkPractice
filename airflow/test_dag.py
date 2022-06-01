from datetime import datetime

from airflow.models import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.python_operator import PythonOperator
from airflow.operators.python_operator import BranchPythonOperator
from airflow.providers.jdbc.hooks.jdbc import JdbcHook
import requests


def read_cred(file, **kwargs):
    confs = ""
    db_cred = [""]*5
    file1 = open(file, "r")

    while True:
        line = file1.readline()
        if line:
            confs += "--conf " + line.strip() + " "
            key, sep, value = line.strip().partition("=")
            if key == "spark.source":
                db_cred[0] = value                
            elif key == "spark.source.url":
                db_cred[1] = value
            elif key == "spark.source.username":
                db_cred[2] = value
            elif key == "spark.source.password":
                db_cred[3] = value
            elif key == "spark.dbtable":
                db_cred[4] = value
        else:
            break
    cred = ' '.join(db_cred)
    task_instance = kwargs['task_instance']
    task_instance.xcom_push(key='all_conf', value=confs)
    task_instance.xcom_push(key='db_conf', value=cred)
    os.system("airflow connections add 'my_db2' --conn-type 'jdbc' --conn-login 'djc70779'  --conn-password 'yghrLjdNyTVQUh70' --conn-host \"jdbc:db2://2f3279a5-73d1-4859-88f0-a6c3e6b4b907.c3n41cmd0nqnrk39u98g.databases.appdomain.cloud\" --conn-port '30756' --conn-schema 'bludb' --conn-extra \'{\"extra__jdbc__drv_clsname\":\"com.ibm.db2.jcc.DB2Driver\",\"extra__jdbc__drv_path\":\"<PATH_TO_SPARK_JAR>/DataEngineer_SparkPractice/target/scala-2.12/sparkPractice-assembly-0.1.0-SNAPSHOT.jar\"}\'")    
    file1.close
   
def check_connection():
    connection = JdbcHook(jdbc_conn_id="db2")
    result=connection.test_connection()
    return "table_exists" if result else "send_failed"
    
def table_exists(**kwargs):
    connection = JdbcHook(jdbc_conn_id="db2")
    ti = kwargs['ti']
    db_conf = ti.xcom_pull(task_ids='read_cred',key='db_conf')
    table_name = db_conf.split()[-1]
    query = "SELECT COUNT(*) FROM " + table_name
    status = "spark_app"
    try:
        result = connection.run(query, autocommit=True)
    except:
        status = "create_table"    
    return  status
    
def sendMessage(message, **kwargs):
    bot_token = '<YOUR_BOT_TOKEN>'
    bot_chatID = '<YOUR_CHAT_ID>' 
    send_text = 'https://api.telegram.org/bot' + bot_token + '/sendMessage?chat_id=' + bot_chatID + '&parse_mode=Markdown&text=' + message
    requests.get(send_text)
 
with DAG(
    dag_id='DAG',
    schedule_interval='* */4 * * *',
    start_date=datetime(2021, 1, 1),
    catchup=False,
    tags=['example']
) as dag:
   
    read_cred = PythonOperator(
        task_id="read_cred",
        provide_context=True,
        python_callable=read_cred,
        op_kwargs={"file": '<FILE_WITH_FULL_PATH>'}
  )
  
    check_connection = BranchPythonOperator(
        task_id="check_connection",
        python_callable=check_connection
    )
    
    table_exists = BranchPythonOperator(
        task_id="table_exists",
        python_callable=table_exists
    )
        
    send_failed = PythonOperator(
        task_id="send_failed", 
        trigger_rule= 'one_failed',
        provide_context=True,
        python_callable=sendMessage,
        op_kwargs={"message": 'Credentials are not valid - Job failed!'}
  )
  
    send_success = PythonOperator(
        task_id="send_success",
        provide_context=True,
        python_callable=sendMessage,
        op_kwargs={"message":  'Job completed!'}
  )
  
  
    
    spark_app = BashOperator(
        trigger_rule = 'one_success',
        task_id="spark_app",
        bash_command='<SPARK_HOME>/spark-submit --master=local[*] ' + "{{ti.xcom_pull(task_ids='read_cred',key='all_conf')}}" + ' --class Main <PATH_TO_SPARK_PROJECT>/DataEngineer_SparkPractice/target/scala-2.12/sparkPractice-assembly-0.1.0-SNAPSHOT.jar'
        
    )
    
    create_table = BashOperator(
        trigger_rule = 'one_success',
        task_id="create_table",
        bash_command='java -cp <PATH_TO_SCALA_PROJECT>/DataEngineer_ScalaPractice/target/scala-2.13/DataEngineer_ScalaPractice-assembly-0.1.0-SNAPSHOT.jar load.LoadStarter ' + "{{ti.xcom_pull(task_ids='read_cred',key='db_conf')}}"
    )
        
    read_cred >> check_connection >> [table_exists, send_failed]
    table_exists >> [create_table, spark_app]
    create_table >> spark_app
    spark_app >> send_success
