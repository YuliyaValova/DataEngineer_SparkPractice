FROM bitnami/spark:latest
USER root
RUN useradd -m -u 1001 spark
USER spark
COPY ./DataEngineer_SparkPractice/target/scala-2.12/sparkpractice-assembly-0.1.0-SNAPSHOT.jar /app/
WORKDIR /app/
