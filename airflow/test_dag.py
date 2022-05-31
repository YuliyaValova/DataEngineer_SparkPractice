from datetime import datetime

from airflow.models import DAG
from airflow.providers.apache.spark.operators.spark_submit import SparkSubmitOperator
from airflow.operators.bash import BashOperator
from airflow.operators.python_operator import PythonOperator
import requests

from datetime import datetime

from airflow.models import DAG
from airflow.providers.apache.spark.operators.spark_submit import SparkSubmitOperator
from airflow.operators.bash import BashOperator
from airflow.operators.python_operator import PythonOperator
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
    file1.close
    
def sendMessage(message, **kwargs):
    bot_token = ''
    bot_chatID = '' 
    send_text = 'https://api.telegram.org/bot' + bot_token + '/sendMessage?chat_id=' + bot_chatID + '&parse_mode=Markdown&text=' + message
    requests.get(send_text)
 
with DAG(
    dag_id='ABash_5',
    schedule_interval=None,
    start_date=datetime(2021, 1, 1),
    catchup=False,
    tags=['example']
) as dag:
   
    read_cred = PythonOperator(
        task_id="read_cred",
        provide_context=True,
        python_callable=read_cred,
        op_kwargs={"file": <FILE_WITH_FULL_PATH>'}
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
  
    
    submit_job = BashOperator(
        task_id="Spark-app",
        bash_command='<SPARK_HOME>/spark-submit --master=local[*] ' + "{{ti.xcom_pull(task_ids='read_cred',key='all_conf')}}" + ' --class Main <PATH_TO_SPARK_PROJECT>/DataEngineer_SparkPractice/target/scala-2.12/sparkPractice-assembly-0.1.0-SNAPSHOT.jar'
        
    )
                   
    create_table = BashOperator(
        trigger_rule= 'one_success',
        task_id="create_table",
        bash_command='java -cp <PATH_TO_SCALA_PROJECT>/DataEngineer_ScalaPractice/target/scala-2.13/DataEngineer_ScalaPractice-assembly-0.1.0-SNAPSHOT.jar load.LoadStarter ' + "{{ti.xcom_pull(task_ids='read_cred',key='db_conf')}}"
    )
    
    read_cred >> [create_table, send_failed] 
    create_table >> submit_job >> send_success
