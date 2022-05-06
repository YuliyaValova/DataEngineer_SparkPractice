# To start a spark job inside the pod(deployment) you need:
1) Download a spark.yaml file.
2) Replace <YOUR_IMAGE_NAME> with your image name.                  
3) Start your k8s, e.g. if you use Minikube:
	```sh
	minikube start
	```
4) Write this to create a deployment on .yaml file base:
	```sh
	kubectl apply -f ./spark.yaml 
	```
5) Write this and find your new pod name:
	```sh
	kubectl get pods 
	```
6) Write this to run spark job inside the pod:
	```sh
	kubectl exec <POD_NAME> -- spark-submit --jars "./*" <SPARK_CONFIGS> --class Main sparkpractice_2.12-0.1.0-SNAPSHOT.jar
	```
	
- POD_NAME - Name of the created pod, that you look at step 5.
- SPARK_CONFIGS - Spark configuration parameters in format "--conf key=value" 
	
