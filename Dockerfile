FROM bitnami/spark:latest
COPY ./DataEngineer_SparkPractice/target/scala-2.12/sparkpractice_2.12-0.1.0-SNAPSHOT.jar ./app
CMD ["spark-submit --master local[3] \
--packages com.ibm.db2:jcc:11.5.7.0 \
 --conf spark.save.type=fs \
 --conf spark.fileName=data \
 --conf spark.path="C:\Users\User\Desktop" \
 --conf spark.source=db2 \
 --conf spark.url="" \
 --conf spark.dbtable=LULKA_TEST1 
 --class Main app/sparkpractice_2.12-0.1.0-SNAPSHOT.jar"]
 
 !!! Креды пока внутри !!!
