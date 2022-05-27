from datetime import datetime

from airflow.models import DAG
from airflow.providers.apache.spark.operators.spark_submit import SparkSubmitOperator
from airflow.operators.bash import BashOperator
from airflow.operators.python_operator import PythonOperator
import requests

def sendFailed():
    message = "Failed"
    bot_token = '...'
    bot_chatID = '...'
    send_text = 'https://api.telegram.org/bot' + bot_token + '/sendMessage?chat_id=' + bot_chatID + '&parse_mode=Markdown&text=' + message
    requests.get(send_text)
 
with DAG(
    dag_id='test1_5',
    schedule_interval=None,
    start_date=datetime(2021, 1, 1),
    catchup=False,
    tags=['example']
) as dag:

  # Reading conf file (now it only reading, but not in use)
    read_cred = BashOperator(
    task_id='read_cred',
    bash_command='while read line; do echo --conf $line; done < /mnt/c/conf/spark.txt'
    )
   
  
    send_failed = PythonOperator(
        task_id="send_failed", 
        trigger_rule= 'one_failed',
        python_callable=sendFailed
  )
    
    submit_job = SparkSubmitOperator(
        trigger_rule= 'one_success',
        task_id="Spark-app",
        conn_id='spark_local',
        name='spark-practice',
        application="/mnt/c/Users/User/DataEngineer_SparkPractice/target/scala-2.12/sparkPractice-assembly-0.1.0-SNAPSHOT.jar",
        conf={
            'spark.path': 
            'spark.access.key': 
            'spark.secret.key': 
            'spark.endpoint': 
            'spark.source.url':
            'spark.source.username': 
            'spark.source.password': 
            'spark.save.type': 
            'spark.source': 
            'spark.fileName': 
            'spark.dbtable': 
        },
        java_class="Main",
    )
    
    read_cred >> [submit_job, send_failed]
