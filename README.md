# DataEngineer_SparkPractice
To run this app you need:
<br>
1) Go to "connection.Properties" and add credentials for connection to db2 on Cloud
2) Make a jar of your project
3) Update this command with your credentials for COS and run in your spark\bin folder from cmd 
<br>
<b>spark-submit --packages com.ibm.db2:jcc:11.5.7.0,com.amazonaws:aws-java-sdk:1.11.46,com.ibm.stocator:stocator:1.1.4 --conf spark.access.key=<i>ACCESS_KEY</i> --conf spark.secret.key=<i>SECRET_KEY</i> --conf spark.endpoint=<i>ENDPOINT</i> --class Main <i>PATH_TO_JAR</i>\<i>NAME_OF_JAR</i>.jar</b>
