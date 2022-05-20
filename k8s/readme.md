# To start a spark job inside the pod you need:
1) Download a spark.yaml file and secrets.txt file.
2) Add credentials and other configs in secrets.txt (in format "key=value" (Parameter's descriptions - [here](https://github.com/YuliyaValova/DataEngineer_SparkPractice/blob/master/README.md)))      
3) Start your k8s, e.g. if you use Minikube:
	```sh
	minikube start
	```
4) Write this to create a secrets pod on secrets.txt file base:
	```sh
	kubectl create secret generic <SECRET_NAME> --from-file ./<PATH_TO_FILE>/secrets.txt
	```
5) Replace <YOUR_IMAGE_NAME> in spark.yaml with name of your docker image, replace <SECRET_NAME> with name of the secret, created in step 4.

6) Write this to create a executable pod on spark.yaml file base:
	```sh
	kubectl apply -f ./spark.yaml 
	```
	
### You can see logs, writing:
```sh
kubectl logs <YOUR_POD_NAME>
```
# To run an app on Spark cluster, that is managed by Kubernetes, you need:	
1) Download a spark-k8s.yaml file and secrets.txt file.
2) Add credentials and other configs in secrets.txt (in format "key=value" (Parameter's descriptions - [here](https://github.com/YuliyaValova/DataEngineer_SparkPractice/blob/master/README.md))). In this case as master you need to write your k8s-master address. For example: k8s://https://kubernetes.default.svc      
3) Start your k8s, e.g. if you use Minikube:
	```sh
	minikube start
	```
4) Configure RBAC: Allow service account default:default access namespace default, writing this coomand
	 ```sh
	kubectl create clusterrolebinding default --clusterrole=edit --serviceaccount=default:default --namespace=default
	```
5) Write this to create a secrets pod on secrets.txt file base:
	```sh
	kubectl create secret generic <SECRET_NAME> --from-file ./<PATH_TO_FILE>/secrets.txt 
	```
	
6) Replace <YOUR_IMAGE_NAME> in spark-k8s.yaml with name of your docker image, replace <SECRET_NAME> with name of the secret, created in step 5.
7) Write this to create a executable pod on spark-k8s.yaml file base:
	```sh
	kubectl apply -f ./spark-k8s.yaml 
	```
	
### You can see logs, writing:
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
