# To start a spark job inside the pod(deployment) you need:
1) Download a spark.yaml file and secrets.yaml file.
2) Replace <YOUR_IMAGE_NAME> with your image name in spark.yaml
3) Add credentials and other configs in secrets.yaml (in format "--conf key=value" (Examples - [here](https://github.com/YuliyaValova/DataEngineer_SparkPractice/blob/master/README.md))      
4) Start your k8s, e.g. if you use Minikube:
	```sh
	minikube start
	```
5) Write this to create a secrets pod on secrets.yaml file base:
	```sh
	kubectl apply -f ./secrets.yaml 
	```
	
6) Write this to create a executable pod on spark.yaml file base:
	```sh
	kubectl apply -f ./spark.yaml 
	```
	
