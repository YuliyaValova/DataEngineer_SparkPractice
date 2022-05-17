# DataEngineer_SparkPractice
This application allows you to start an ETL process consisting of three stages:
- Uploading data from DB2 on Cloud or from MySQL (Coming soon...) to Dataframe format, with the following columns:
```sh
+----------+-------------+----+-------+-------+-------+-------+-------+-------+-------+-------+-------+--------+--------+--------+
|PRODUCT_ID|PRODUCT_GROUP|YEAR|MONTH_1|MONTH_2|MONTH_3|MONTH_4|MONTH_5|MONTH_6|MONTH_7|MONTH_8|MONTH_9|MONTH_10|MONTH_11|MONTH_12|
+----------+-------------+----+-------+-------+-------+-------+-------+-------+-------+-------+-------+--------+--------+--------+
```
- Transform the data like this:
   * For each row calculate total of monthly purchases
   * Save the year total as a new column year_purchases
   * Remove the columns with monthly amounts from the data frame
```sh
+----------+-------------+----+-------+------+
|PRODUCT_ID|PRODUCT_GROUP|YEAR|year_purchases|
+----------+-------------+----+-------+------+
```
- Save the data in CSV format to Cloud Object Storage or locally in several files, partitioned by year.

## Requirements
Strongly recomended to use:

| Name | Version |
| ------ | ------ |
| Spark | 3.1.3 |
| Hadoop | 3.2 |
| SBT | 1.6.2 |
| JDK | 11 |
| Scala | 2.12.10 |
| Git | 2.33.0 |

## Build
>All next steps you can do from <b>cmd</b> or <b>PowerShell</b>.
 1. Clone the current version of the repository to your computer.
```sh
 git clone https://github.com/YuliyaValova/DataEngineer_SparkPractice
```
2. Go to the project folder and write this commands:
 * To reload project using sbt.
```sh
sbt reload
```
  * To package project into jar.
```sh
sbt assembly
```
>This process may take some time.
>Check that the .\target\scala-2.12\sparkPractice-assembly-0.1.0-SNAPSHOT.jar has appeared before proceeding to the running application.

## Run
 Update this command with your credentials for COS & DB2 and run in your spark\bin folder from cmd
```sh
spark-submit \
--conf spark.master=<SPARK_MASTER> \
--conf spark.source=<SOURCE_DB> \
--conf spark.save.type=<TYPE> \
--conf spark.path=<PATH> \
--conf spark.fileName=<FILE_NAME> \
--conf spark.access.key=<ACCESS_KEY> \
--conf spark.secret.key=<SECRET_KEY> \
--conf spark.endpoint=<ENDPOINT> \
--conf spark.url=<CONNECTION_URL> \
--conf spark.username=<USERNAME> \
--conf spark.password=<PASSWORD> \
--conf spark.dbtable=<TABLE> \
--class Main <PATH_TO_JAR>\<NAME_OF_JAR>.jar
```
    
- SPARK_MASTER - The master URL for the cluster (e.g. spark://23.195.26.187:7077 or local[3])
- SOURCE_DB - Database from which you extract data. Types currently supported: "mysql", "db2".
- TYPE - Type of data destination. Can take one of two values: 
  * "fs" (for saving to the computer's file system) 
  * "cos" (for saving to the cloud). <br>
- PATH - Name of the bucket in COS instanse (for "cos" type of saving) or path to your computer's folder(for "fs" type of saving). <br>
- FILE_NAME - Name under which the file will be saved. <br>
- ACCESS_KEY (Optional - only for "cos" type of saving) - Access key from credentials for connection to Cloud Object Storage. <br>
- SECRET_KEY (Optional - only for "cos" type of saving) - Secret key from credentials for connection to Cloud Object Storage. <br>
- ENDPOINT (Optional - only for "cos" type of saving) - Endpoint for connection to Cloud Object Storage. <br>
- CONNECTION_URL - Url for jdbc connection. It will looks like :
  * "jdbc:db2://host:port/db_name" (For db2 SOURCE_DB)
  * "jdbc:mysql://host:port/db_name" (For mysql SOURCE_DB)
- USERNAME - Your database username.
- PASSWORD - Password for database connection.
- TABLE - Table name from which the data will be uploaded. <br>
- PATH_TO_JAR - The place where the jar is located. Default jar file location: .\\<project_downloaded_in_step_1>\target\scala-2.12 <br>
- NAME_OF_JAR - Name of the jar. According to the build.sbt it is "sparkPractice_2.12-0.1.0-SNAPSHOT" <br>

### Example for saving data in local file system ("fs" type) with "db2" as source
```sh
spark-submit \
--conf spark.master=local[3] \
--conf spark.source=db2 \
--conf spark.save.type=fs \
--conf spark.path="C:\Users\User\Desktop" \
--conf spark.fileName=data \
--conf spark.url=jdbc:db2://b1bc1111-6v15-8cd4-dop4-10cf777777bf.c1ogj3sd0qgqu0lqde00.databases.appdomain.cloud:37506/bludb \
--conf spark.username=qq11111 \
--conf spark.password=AAA11Aaa1a111Aaa \
--conf spark.dbtable=table \
--class Main C:\Users\User\DataEngineer_SparkPractice\target\scala-2.12\sparkPractice-assembly-0.1.0-SNAPSHOT.jar
``` 

### Example for saving data in Cloud Object Storage ("cos" type) with "mysql" as source
```sh
spark-submit \
--conf spark.master=local[3] \
--conf spark.source=mysql \
--conf spark.save.type=cos \
--conf spark.path=storage-test \
--conf spark.fileName=data.csv \
--conf spark.access.key=a90pfa76a5ia48adb4a0e9dc66s3e54d \ 
--conf spark.secret.key=e4539740933ef78888c4b8b24e1q1f9e7m0729db4444bb68 \
--conf spark.endpoint=http://s3.eu-de.cloud-object-storage.appdomain.cloud \
--conf spark.url=jdbc:mysql://localhost:3306/db-name \
--conf spark.username=root \
--conf spark.password=root \
--conf spark.dbtable=table \
--class Main C:\Users\User\DataEngineer_SparkPractice\target\scala-2.12\sparkPractice-assembly-0.1.0-SNAPSHOT.jar
``` 
