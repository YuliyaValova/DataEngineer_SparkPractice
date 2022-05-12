# To start a spark job inside the pod(deployment) you need:
1) Download a spark.yaml file.
2) Replace <YOUR_IMAGE_NAME> with your image name, <CONFIGS> with --conf 
   * Spark configuration parameters in format "--conf key=value" (Examples - [here](https://github.com/YuliyaValova/DataEngineer_SparkPractice/blob/master/README.md)))       
3) Start your k8s, e.g. if you use Minikube:
	```sh
	minikube start
	```
4) Write this to create a deployment on .yaml file base:
	```sh
	kubectl apply -f ./spark.yaml 
	```
	
