# Run a spark app (now just run all via spark-submit)
1. Download this dag.py file, add parameters and locate it in your Airflow's dags_folder.
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
![image](https://user-images.githubusercontent.com/73712980/170561451-f822a87b-c828-456d-8aee-34279eadd2e2.png)

6. Now trigger your dag

> Be sure, that you have JAVA_HOME and SPARK_HOME at your workstation.

