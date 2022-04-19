# DataEngineer_SparkPractice
This application allows you to start an ETL process consisting of three stages:
1) Uploading data from DB2 on Cloud to Dataframe format, with the following scheme:

    ![image](https://user-images.githubusercontent.com/73712980/163964020-6ccea6c1-4d99-4788-bbf0-e66d4d84867d.png)
    
2) Transform the data like this:
   * For each row calculate total of monthly purchases
   * Save the year total as a new column year_purchases
   * Remove the columns with monthly amounts from the data frame

    ![image](https://user-images.githubusercontent.com/73712980/163964187-d6451539-554f-4575-b728-88d28b14e2a4.png)
 
3) Save the data in CSV format to Cloud Object Storage or locally at the specified path.
4) A more detailed description of the methods is located in the code of the corresponding interfaces.

# To run this app you need:
<br>
1. Go to "connection.Properties" and add credentials for connection to db2 on Cloud
<br>
2. Make a jar of your project
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
--class Main <i>PATH_TO_JAR</i>\<i>NAME_OF_JAR</i>.jar</b>

# Quick test
<br>
You can test this app quickly using pre-made jars - part.jar (with partitioning) and withoutPart.jar (without partitioning).
