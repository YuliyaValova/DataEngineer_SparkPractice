from datetime import datetime

from airflow.models import DAG
from airflow.providers.apache.spark.operators.spark_submit import SparkSubmitOperator

with DAG(
    dag_id='test8',
    schedule_interval=None,
    start_date=datetime(2021, 1, 1),
    catchup=False,
    tags=['example']
) as dag:
    submit_job = SparkSubmitOperator(
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
    submit_job
