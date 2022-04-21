# DataEngineer_SparkPractice

## Functionality
This application allows you to start an ETL process consisting of three stages:
- Uploading data from DB2 on Cloud to Dataframe format, with the following scheme: (Coming soon...)
- Transform the data like this:
   * For each row calculate total of monthly purchases
   * Save the year total as a new column year_purchases
   * Remove the columns with monthly amounts from the data frame
- Save the data in CSV format to Cloud Object Storage in three files, partitioned by year.

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

## How to run application
>All next steps you can do from <b>cmd</b> or <b>PowerShell</b>.
1. Clone the current version of the repository to your computer.
```sh
 git clone https://github.com/YuliyaValova/DataEngineer_SparkPractice
```
2. Go to the project folder.
```sh
cd DataEngineer_SparkPractice
```
3. Write this command to start sbt.
```sh
sbt 
```
4. Write to reload project. 
```sh
reload
```
5. Write to make a jar of the folder.
```sh
package
```
>This process may take some time.
>Check that the file has appeared before proceeding to the next step.
  
6. Update this command with your credentials for COS & DB2 and run in your spark\bin folder from cmd

<br>./bin/spark-submit \
<br>
--packages com.ibm.db2:jcc:11.5.7.0,com.amazonaws:aws-java-sdk:1.11.46,com.ibm.stocator:stocator:1.1.4 \
<br>
--conf spark.access.key=<ACCESS_KEY> \
<br>
--conf spark.secret.key=<SECRET_KEY> \
<br>
--conf spark.endpoint=<ENDPOINT_> \
<br>
--conf spark.bucket=<BACKET_NAME> \
<br>
--conf spark.fileName=<FILE_NAME> \
<br>
--conf spark.db2.url=<DB2_CONNECTION_URL> \
<br>
--conf spark.db2.dbtable=<DB2_TABLE> \
<br>
--class Main <PATH_TO_JAR>\<NAME_OF_JAR>.jar</b>

>ACCESS_KEY - Access key from credentials for connection to Cloud Object Storage. <br>
>SECRET_KEY - Secret key from credentials for connection to Cloud Object Storage. <br>
>ENDPOINT - Endpoint for connection to Cloud Object Storage. <br>
>BACKET_NAME - Name of the bucket in COS instanse. <br>
>FILE_NAME - Тame under which the file will be saved to the bucket. <br>
>DB2_CONNECTION_URL - Url for jdbc connection to DB2 on Cloud. It will looks like : "jdbc:db2://url/db_name:user=...;password=...;sslConnection=true;" <br>
>DB2_TABLE - Table name from which the data will be uploaded. <br>
>PATH_TO_JAR - The place where the jar is located. Default jar file location: .\<project_downloaded_in_step_1>\target\scala-2.12 <br>
>NAME_OF_JAR - Name of the jar. According to the build.sbt it is "sparkPractice_2.12-0.1.0-SNAPSHOT" <br>

<b> Example </b>
```sh
spark-submit \
--packages com.ibm.db2:jcc:11.5.7.0,com.amazonaws:aws-java-sdk:1.11.46,com.ibm.stocator:stocator:1.1.4 \
--conf spark.access.key=a90pfa76a5ia48adb4a0e9dc66s3e54d \ 
--conf spark.secret.key=e4539740933ef78888c4b8b24e1q1f9e7m0729db4444bb68 \
--conf spark.endpoint=http://s3.eu-de.cloud-object-storage.appdomain.cloud \
--conf spark.fileName=data.csv \
--conf spark.bucket=storage-test \
--conf spark.db2.url="jdbc:db2://b1bc1111-6v15-8cd4-dop4-10cf777777bf.c1ogj3sd0qgqu0lqde00.databases.appdomain.cloud:37506/bludb:user=qq11111;password=AAA11Aaa1a111Aaa;sslConnection=true;" \
--conf spark.db2.dbtable=table \
--class Main C:\Users\User\DataEngineer_SparkPractice\target\scala-2.12\untitled5_2.12-0.1.0-SNAPSHOT.jar
``` 

# Quick test
<br>
You can test this app quickly using pre-made jar - quickTest.jar
<br>
Just download it add use in spark-submit like in step 6.
