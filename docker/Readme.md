# To create and run a docker image of the project you need:
1) Go to the directory where the project folder is located.
2) Place in it this Dockerfile.
3) Run this command to build an image:
	```sh
	docker build -t <IMAGE_NAME> .
	```
4) Run this command to run the image and start a job:
	```sh
	docker run <IMAGE_NAME> spark-submit --master local[3] <SPARK_CONFIGS> --class Main sparkPractice-assembly-0.1.0-SNAPSHOT.jar
	```
	
# Example for docker run (for DB2 and COS)
- Create an image:
	```sh 
	docker build -t spark .
	```
- Run app:
	```sh
	docker run spark spark-submit \
	--master local[3] \
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
	--class Main sparkPractice-assembly-0.1.0-SNAPSHOT.jar
	```
