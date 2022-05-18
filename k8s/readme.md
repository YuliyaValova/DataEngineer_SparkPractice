# To start a spark job inside the pod you need:
1) Download a spark.yaml file and secrets.yaml file.
2) Replace <YOUR_IMAGE_NAME> with your image name in spark.yaml
3) Add credentials and other configs in secrets.yaml (in format "key: value" (Parameter's descriptions - [here](https://github.com/YuliyaValova/DataEngineer_SparkPractice/blob/master/README.md)))      
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
	
You can see logs, writing:
```sh
kubectl logs <YOUR_POD_NAME>
```
# To run an app on Spark cluster, that is managed by Kubernetes, you need:	
1) Download a spark.yaml file and secrets.yaml file.
2) Replace <YOUR_IMAGE_NAME> with your image name in spark-k8s.yaml
3) Add credentials and other configs in secrets.yaml (in format "key: value" (Parameter's descriptions - [here](https://github.com/YuliyaValova/DataEngineer_SparkPractice/blob/master/README.md)))      
4) Start your k8s, e.g. if you use Minikube:
	```sh
	minikube start
	```
5) Configure RBAC: Allow service account default:default access namespace default, writing this coomand
 ```sh
kubectl create clusterrolebinding default --clusterrole=edit --serviceaccount=default:default --namespace=default
```
6) Write this to create a secrets pod on secrets.yaml file base:
	```sh
	kubectl apply -f ./secrets.yaml 
	```
	
7) Write this to create a executable pod on spark-k8s.yaml file base:
	```sh
	kubectl apply -f ./spark.yaml 
	```
	
You can see logs, writing:
```sh
kubectl logs <YOUR_POD_NAME>
```
To see application status, you can see driver logs.
```sh
kubectl get pods 
```
Find a pod with name like "main-...-driver" and then write
```sh
kubectl logs <POD_DRIVER_NAME>
```
