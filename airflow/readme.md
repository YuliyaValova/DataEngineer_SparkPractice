# Current status:
At the moment, this dag can only check for the presence of a config file in the specified path, and depending on this, choose one of two paths:
1) If there is no file, send an error message to telegram
2) If the file exists, the spark part of the application is executed (at the same time, the configs are still taken from the --conf, but not from config file) and in case success sends a message in Telegram.
# DAG
![image](https://user-images.githubusercontent.com/73712980/170789012-96e7ebd1-ae10-4204-b3cd-dbeae998ec64.png)
#  Run a spark app (now just run all via spark-submit)
1. Download this dag.py file, add parameters (database, cos, app and telegram conf) and locate it in your Airflow's dags_folder.
2. Write this to initialize Airflow database:
    ```sh
    airflow db init
    ```
3. Write this to start Airflow Scheduler:
   ```sh
   airflow scheduler
   ```
4. Open new terminal and write this to start UI on localhost:8080
    ```sh
   airflow webserver -p 8080 
   ```
5. Go to the UI: Admin->Connections->Add new and create spark_local connection to run a job locally.
![image](https://user-images.githubusercontent.com/73712980/170561684-faa75692-06d3-46f6-a8ea-cc38c3d77f40.png)
Don't forget to set your spark-home location in Extra!
6. Now trigger your dag

> Be sure, that you have JAVA_HOME at your workstation.

