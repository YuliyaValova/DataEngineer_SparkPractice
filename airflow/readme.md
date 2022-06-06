# Current status:
At the moment, this dag:
1. Read credentials and app configs from Airflow JSON Variable
2. Check that DB creadentials are valid (if not - send "Failed" message to the Telegram)
3. Check if the source db table exists, if yes, jump to step 5.
4. Create and fill the table in DB2
5. Run ETL process (read from db2, calc sum per year, load to cos) with spark
6. Send "Success" message to the Telegram 
7. Job scheduled every 4 hours
# DAG
![image](https://user-images.githubusercontent.com/73712980/171383721-d0feed53-d811-4542-8f13-7f12082f67d9.png)
#  Run
1. Create Airflow Variable "conf" (UI:Admin->Variables->"+") with parameters for db2/cos connection and app properties in a JSON-format.
![image](https://user-images.githubusercontent.com/73712980/172159407-c2b591a1-557c-4b6d-936d-44f9972d6480.png)
```sh
{
  "spark.save.type": "cos",
  "spark.source": "db2",
  "spark.path": " ",
  "spark.fileName": " ",
  "spark.dbtable": " ",
  "spark.source.url": " ",
  "spark.source.username": " ",
  "spark.source.password": " ",
  "spark.access.key": " ",
  "spark.secret.key": " ",
  "spark.endpoint": " "
}
```
2. Create spark-connection "spark_local" (UI: Admin->Connections->"+") 
![image](https://user-images.githubusercontent.com/73712980/172160094-75e7a8fc-b83a-4710-ad1c-cae04a0d424b.png)
```sh
{"master": "local[*]", "spark-home": "<YOUR_SPARK_HOME>", "spark_binary": "spark-submit", "namespace": "default"}
```
3. Download this dag.py file, add missing parameters and locate it in your Airflow's dags_folder.
    - <YOUR_BOT_TOKEN> - telegram-bot token for sending messages
    - <YOUR_CHAT_ID> - id of your telegram chat
    - <PATH_TO_SCALA/SPARK_PROJECT> - path to place where your project folder (DataEngineer_SparkPractice/DataEngineer_ScalaPractice) is located
    
   Check that you change <PATH_TO_SPARK_PROJECT> in 2 places (lines 22 and 96)
    
4. Write this to initialize Airflow database:
    ```sh
    airflow db init
    ```
5. Write this to start Airflow Scheduler:
   ```sh
   airflow scheduler
   ```
6. Open new terminal and write this to start UI on localhost:8080
    ```sh
   airflow webserver -p 8080 
   ```
7. Turn on your dag
<br>

   > Be sure, that you have JAVA_HOME at your workstation.
