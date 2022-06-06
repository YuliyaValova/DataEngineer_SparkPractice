from datetime import datetime

from airflow.models import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.python_operator import PythonOperator
from airflow.providers.apache.spark.operators.spark_submit import SparkSubmitOperator
from airflow.operators.python_operator import BranchPythonOperator
from airflow.providers.jdbc.hooks.jdbc import JdbcHook
import requests
import json
import os
from airflow.models import Variable
config  = Variable.get("conf", deserialize_json=True)

def read_cred(conf_json, **kwargs):
    conf_json = json.dumps(conf_json)
    param = json.loads(conf_json)
    db_conf = param["spark.source"] + " " + param["spark.source.url"] + " " + param["spark.source.username"] + " " + param["spark.source.password"] + " " +  param["spark.dbtable"]
    login = param["spark.source.username"]
    password = param["spark.source.password"]
    host = param["spark.source.url"] + ":sslConnection=true;"
    os.system("airflow connections add 'my_db2' --conn-type 'jdbc' --conn-login '" + login + "' --conn-password '" + password + "' --conn-host '" + host + "' --conn-extra \'{\"extra__jdbc__drv_clsname\":\"com.ibm.db2.jcc.DB2Driver\",\"extra__jdbc__drv_path\":\"<PATH_TO_SPARK_PROJECT>/DataEngineer_SparkPractice/target/scala-2.12/sparkPractice-assembly-0.1.0-SNAPSHOT.jar\"}\'")
    os.system("airflow variables set secret '" + db_conf + "'")
   
def check_connection():
    connection = JdbcHook(jdbc_conn_id="my_db2")
    connection._test_connection_sql  = "values 666;"
    status, message=connection.test_connection()
    return "send_failed"  if status==False else "table_exists"
    
def table_exists(**kwargs):
    connection = JdbcHook(jdbc_conn_id="my_db2")
    db_conf = json.dumps(config)
    param = json.loads(db_conf)
    table_name = param["spark.dbtable"]
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
        op_kwargs={"conf_json": config}
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
  
  
    
    spark_app = SparkSubmitOperator(
        task_id="spark_app",
        trigger_rule = 'none_failed',
        conn_id='spark_local',
        name='spark-practice',
        application="<PATH_TO_SPARK_PROJECT>/DataEngineer_SparkPractice/target/scala-2.12/sparkPractice-assembly-0.1.0-SNAPSHOT.jar",
        conf=config,
        java_class="Main"
        
    )
    
    create_table = BashOperator(
        trigger_rule = 'one_success',
        task_id="create_table",
        bash_command='java -cp <PATH_TO_SCALA_PROJECT>/DataEngineer_ScalaPractice/target/scala-2.13/DataEngineer_ScalaPractice-assembly-0.1.0-SNAPSHOT.jar load.LoadStarter ' + Variable.get("secret", default_var="")  
    )
        
    read_cred >> check_connection >> [table_exists, send_failed]
    table_exists >> [create_table, spark_app]
    create_table >> spark_app
    spark_app >> send_success
