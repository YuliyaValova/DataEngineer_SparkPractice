FROM bitnami/spark:latest
COPY ./DataEngineer_SparkPractice/target/scala-2.12/sparkpractice_2.12-0.1.0-SNAPSHOT.jar /app/
COPY ./DataEngineer_SparkPractice/lib/* /app/
WORKDIR app

Запуск (Source-DB2, Destination-COS):
docker run spark spark-submit --master local[3] --jars "./*" \
--conf spark.save.type=cos \
--conf spark.path=(bucket) \
--conf spark.fileName=”lulka/data1.csv” \
--conf spark.access.key=... \
--conf spark.secret.key=... 
--conf spark.endpoint=https://... \
--conf spark.source=db2 \
--conf spark.source.url= ... \
--conf spark.source.username=... \
--conf spark.source.password=... \
--conf spark.dbtable=LULKA_TEST1 \
--class Main sparkpractice_2.12-0.1.0-SNAPSHOT.jar
