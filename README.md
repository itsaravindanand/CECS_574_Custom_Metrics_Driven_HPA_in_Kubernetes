# CECS_574_Custom_Metrics_Driven_HPA_in_Kubernetes

## Prerequisites

- IntelliJ or Eclipse or Similar to run the Java Spring Boot Application
- Docker
- Minikube
- Visual Studio or Similar to run the ReactJS application

## Implementation Steps

### Step 1: Start the MySQL Container in Docker

Execute the following command to start MySQL Server in Docker:

```bash
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=tdc-test -e MYSQL_USER=tdc-user -e MYSQL_PASSWORD=tdc-pw -p 3307:3306 -d mysql:8-oracle
```

### Step 2: Open the Java Spring Boot Application

Open the application from the repository in your IDE.

If required, update the Database Connection details in the application.properties file in the resources folder

```bash
spring.datasource.url=jdbc:mysql://host.docker.internal:3307/tdc-test
spring.datasource.username=tdc-user
spring.datasource.password=tdc-pw
```

### Step 3: Run the Java Application

Run the Java application and perform a maven clean install. This will add the .jar for the application in the target folder.

### Step 4: Build Docker Image Using Dockerfile

Build the Docker image using the following command from the root folder of the Orchestrator Project:

```bash
docker build -t orchestrator:latest .
```

### Step 5: Create a Cluster

Create a cluster using this command:

```bash
kind create cluster
```

Check cluster using the following command:

```bash
kind get clusters
```

### Step 6: Load the Docker Image of the Java Application into the Cluster

Load the Docker image into the cluster with the following command:

```bash
kind load docker-image orchestrator:latest
```

### Step 7: Deploy the Application Using Orchestrator.yaml

Deploy the application using the command below:

```bash
kubectl apply -f ./k8s/orchestrator.yaml
```

### Step 8: Check the Pods and Services

Verify the running pods and services with these commands:

Get all active pods
```bash
kubectl get pods
```
Get all active services
```bash
kubectl get svc
```

### Step 9: Port-Forward the Containers

Port-forward the containers for API calls and the Prometheus HTTP server:
API calls service
```bash
kubectl port-forward svc/orchestrator-service 8080:8080
```

Metrics Service
```bash
kubectl port-forward svc/orchestrator-metrics 1234:1234
```



### Step 10: Setting Up Prometheus

Apply the configuration and install Prometheus using Helm with the `prom-server-conf.yaml` file:

```bash
kubectl apply -f ./k8s/prom-server-conf.yaml
```

Check the configuration:

```bash
kubectl get cm
```

Install Prometheus using Helm:

Update Helm Repository

```bash
helm repo update
```

Install Prometheus using Helm with the `prom-values.yaml` file:
```bash
helm install prometheus prometheus-community/prometheus -f ./k8s/prom-values.yaml
```
Get all active pods
```bash
kubectl get pods
```
Get all active services
```bash
kubectl get svc
```
Port forward the Prometheus service:

```bash
kubectl port-forward svc/prometheus-server 8081:80
```

### Step 11: Setting Up the Prometheus Adapter

Install the Prometheus adapter using Helm with the `prom-adapter-values.yaml` file:

```bash
helm install prometheus-adapter prometheus-community/prometheus-adapter -f ./k8s/prom-adapter-values.yaml
```

Get all active pods
```bash
kubectl get pods
```

Validate the adapter:

```bash
kubectl get --raw "/apis/custom.metrics.k8s.io/v1beta1/namespaces/default/pods/*/queue_length"
```

### Step 12: Setting Up the Horizontal Pod Autoscaler (HPA)

Install HPA using the `hpa.yaml` file:

```bash
kubectl apply -f ./k8s/hpa.yaml
```

Check the HPA using the following command:

```bash
kubectl get hpa
```

Check the HPA details:

```bash
kubectl describe hpa orchestrator-hpa
```

Check the replication actions in the deployment:

```bash
kubectl describe deploy orchestrator-deploy
```

### Step 13: Run the Application to add tasks:

Download the react application project and run the application with this command:

```bash
npm start
```

### React Application Screenshots:

#### Check the custom metric and add tasks to the orchestrator:

![Custom_Metric_and_Add_Task](https://github.com/itsaravindanand/CECS_574_Custom_Metrics_Driven_HPA_in_Kubernetes/blob/main/Project_Files/Documentation/Screenshots/Checking_Custom_Metric_and_add_task.png)

#### Manage Tasks:

![Task_Queue](https://github.com/itsaravindanand/CECS_574_Custom_Metrics_Driven_HPA_in_Kubernetes/blob/main/Project_Files/Documentation/Screenshots/Task_Queue.png)

### Kubernetes Logs Screenshots

#### Describe HPA:

The HPA has scaled up and down based on the queue_length value

![Describe_HPA](https://github.com/itsaravindanand/CECS_574_Custom_Metrics_Driven_HPA_in_Kubernetes/blob/main/Project_Files/Documentation/Screenshots/Describe_HPA_Changes.png)

#### Describe Deploy:

The Orchestrator deployment is scaled up and down based on the queue_length value

![Describe_Deploy](https://github.com/itsaravindanand/CECS_574_Custom_Metrics_Driven_HPA_in_Kubernetes/blob/main/Project_Files/Documentation/Screenshots/Describe_Deploy_Changes.png)


