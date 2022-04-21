# DataEngineer_SparkPractice
This application allows you to start an ETL process consisting of three stages:
1) Uploading data from DB2 on Cloud to Dataframe format, with the following scheme:
***
2) Transform the data like this:
   * For each row calculate total of monthly purchases
   * Save the year total as a new column year_purchases
   * Remove the columns with monthly amounts from the data frame
*** 
3) Save the data in CSV format to Cloud Object Storage

# To run this app you need:
<br>
1. Pull the current version of this repo to your computer.
<br>
2. Make a jar of this project using sbt shell: sbt package. By default it's location: .\target\scala-2.12    
<br>
3. Update this command with your credentials for COS and run in your spark\bin folder from cmd 
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

# Quick test
<br>
You can test this app quickly using pre-made jar - quickTest.jar
