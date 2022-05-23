# To start a spark job inside the pod you need:
1) Download a spark.yaml file, database.yaml, storage.yaml and database-storage.yaml files.
2) Add credentials and other configs in  database.yaml(parameters for source db connection), storage.yaml(parameters for connection to the storage) and database-storage.yaml(app properties) files (in format "key: value" (Parameter's descriptions - [here](https://github.com/YuliyaValova/DataEngineer_SparkPractice/blob/master/README.md)))      
3) Start your k8s, e.g. if you use Minikube:
	```sh
	minikube start
	```
4) Write this to create a secret's pod on  database.yaml file base:
	```sh
	kubectl apply -f ./database.yaml
	```
5) Write this to create a secret's pod on  storage.yaml file base:
	```sh
	kubectl apply -f ./storage.yaml
	```
6) Write this to create a ConfigMap on  database-storage.yaml file base:
	```sh
	kubectl create configmap <CONFIG_MAP_NAME> --from-file= database-storage.yaml
	```
7) Replace <YOUR_IMAGE_NAME> in spark.yaml with name of your docker image, replace <CONFIG_MAP_NAME> with name of the ConfigMap, created in step 6.

8) Write this to create a executable pod on spark.yaml file base:
	```sh
	kubectl apply -f ./spark.yaml 
	```
	
### You can see logs, writing:
```sh
kubectl logs <YOUR_POD_NAME>
```
# To run an app on Spark cluster, that is managed by Kubernetes, you need:	
1) Download a spark-k8s.yaml file, database.yaml, storage.yaml and database-storage.yaml files.
2) Add credentials and other configs in  database.yaml(parameters for source db connection), storage.yaml(parameters for connection to the storage) and database-storage.yaml(app properties) files (in format "key: value" (Parameter's descriptions - [here](https://github.com/YuliyaValova/DataEngineer_SparkPractice/blob/master/README.md)))      
3) Start your k8s, e.g. if you use Minikube:
	```sh
	minikube start
	```
4) Configure RBAC: Allow service account default:default access namespace default, writing this coomand
	 ```sh
	kubectl create clusterrolebinding default --clusterrole=edit --serviceaccount=default:default --namespace=default
	```
5) Write this to create a secret's pod on  database.yaml file base:
	```sh
	kubectl apply -f ./database.yaml
	```
6) Write this to create a secret's pod on  storage.yaml file base:
	```sh
	kubectl apply -f ./storage.yaml
	```
7) Write this to create a ConfigMap on  database-storage.yaml file base:
	```sh
	kubectl create configmap <CONFIG_MAP_NAME> --from-file= database-storage.yaml
	```
8) Replace <YOUR_IMAGE_NAME> in spark.yaml with name of your docker image, replace <CONFIG_MAP_NAME> with name of the ConfigMap, created in step 6.

9) Write this to create a executable pod on spark-k8s.yaml file base:
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
