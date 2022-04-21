# DataEngineer_SparkPractice

## Functionality
This application allows you to start an ETL process consisting of three stages:
- Uploading data from DB2 on Cloud to Dataframe format, with the following scheme:
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
>Default jar file location: .\target\scala-2.12
  
6. Update this command with your credentials for COS & DB2 and run in your spark\bin folder from cmd

<br>
<br>./bin/spark-submit \
<br>
--packages com.ibm.db2:jcc:11.5.7.0,com.amazonaws:aws-java-sdk:1.11.46,com.ibm.stocator:stocator:1.1.4 \
<br>
--conf spark.access.key=<i>ACCESS_KEY</i> \
<br>
--conf spark.secret.key=<i>SECRET_KEY</i> \
<br>
--conf spark.endpoint=<i>ENDPOINT</i> \
<br>
--conf spark.bucket=<i>BACKET_NAME</i> \
<br>
--conf spark.fileName=<i>FILE_NAME</i> \
<br>
--conf spark.db2.url=<i>DB2_CONNECTION_URL*</i> \
<br>
--conf spark.db2.dbtable=<i>DB2_TABLE*</i> \
<br>
--class Main <i>PATH_TO_JAR</i>\<i>NAME_OF_JAR</i>.jar</b>

<b> Example </b>
<br>
```sh
``` 

# Quick test
<br>
You can test this app quickly using pre-made jar - quickTest.jar
