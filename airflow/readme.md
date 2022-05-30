# Current status:
At the moment, this dag can only check for the presence of a config file in the specified path, and depending on this, choose one of two paths:
1) If there is no file, send an error message to telegram
2) If the file exists, the spark part of the application is executed and in case success sends a message in Telegram.
# DAG
![image](https://user-images.githubusercontent.com/73712980/170789012-96e7ebd1-ae10-4204-b3cd-dbeae998ec64.png)
#  Run a spark app (now just run all via spark-submit)
1. Download conf.txt file and add parameters for db2/cos connection and app properties.
2. Download this dag.py file, add missing parameters and locate it in your Airflow's dags_folder.
    - <SPARK_HOME> - path to your spark\bin folder
    - <FILE_WITH_FULL_PATH> - full path and name of the config file, downloaded in step 1
    - <PATH_TO_JAR> - path to place where your project folder (DataEngineer_SparkPractice) is located
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
6. Now trigger your dag
<br>

   > Be sure, that you have JAVA_HOME at your workstation.

# Problems:
Since I run the application through BashOperator and not through the SparkSubmitOperator (why - in not_work folder), confidential information is broadcast in the logs. There is a similar problem in the read_cred task, since in the logs you can see configs from the file in the value, returned by the function.

