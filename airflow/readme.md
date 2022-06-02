# Current status:
At the moment, this dag:
1. Read credentials and app configs from file
2. Check that DB creadentials are valid (if not - send "Failed" message to the Telegram)
3. Check if the source db table exists, if yes, jump to step 5.
4. Create and fill the table in DB2
5. Run ETL process (read from db2, calc sum per year, load to cos) with spark
6. Send "Success" message to the Telegram 
# DAG
![image](https://user-images.githubusercontent.com/73712980/171383721-d0feed53-d811-4542-8f13-7f12082f67d9.png)
#  Run
1. Download conf.txt file and add parameters for db2/cos connection and app properties.
2. Download this dag.py file, add missing parameters (also bot_token and chat_id) and locate it in your Airflow's dags_folder.
    - <YOUR_BOT_TOKEN> - telegram-bot token for sending messages
    - <YOUR_CHAT_ID> - id of your telegram chat
    - <SPARK_HOME> - path to your spark\bin folder 
    - <FILE_WITH_FULL_PATH> - full path and name of the config file, downloaded in step 1
    - <PATH_TO_SCALA/SPARK_PROJECT> - path to place where your project folder (DataEngineer_SparkPractice/DataEngineer_ScalaPractice) is located
    
   Check that you change <PATH_TO_SPARK_PROJECT> in 2 places
    
3. Write this to initialize Airflow database:
    ```sh
    airflow db init
    ```
4. Write this to start Airflow Scheduler:
   ```sh
   airflow scheduler
   ```
5. Open new terminal and write this to start UI on localhost:8080
    ```sh
   airflow webserver -p 8080 
   ```
6. Trigger your dag
<br>

   > Be sure, that you have JAVA_HOME at your workstation.

# Problems:
- Since I run the application through BashOperator and not through the SparkSubmitOperator (why - in not_work folder), confidential information is broadcast in the logs.

