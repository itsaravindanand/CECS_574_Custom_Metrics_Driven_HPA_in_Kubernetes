# CECS_574_Custom_Metrics_Driven_HPA_in_Kubernetes

## Prerequisites

- Java
- IDE - IntelliJ or Eclipse to run the Java Spring Boot Application
- Docker
- Minikube

## Implementation Steps

### Step 1: Open the Java Spring Boot Application

Open the application from the repository in your IDE.

### Step 2: Start the MySQL Server in Docker

Execute the following command to start MySQL Server in Docker:

```bash
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_DATABASE=tdc-test -e MYSQL_USER=tdc-user -e MYSQL_PASSWORD=tdc-pw -p 3307:3306 -d mysql:8-oracle
```

### Step 3: Run the Java Application

Run the Java application and check for errors.

### Step 4: Build Docker Image Using Dockerfile

Build the Docker image using the following command:

```bash
docker build -t orchestrator:latest .
```

### Step 5: Create a Cluster

Create a cluster using the following command:

```bash
kind create cluster
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

### Step 8: Check the Pod and Service

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

```bash
helm install prometheus prometheus-community/prometheus -f ./k8s/prom-values.yaml
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

Validate the adapter:

```bash
kubectl get --raw "/apis/custom.metrics.k8s.io/v1beta1/namespaces/default/pods/*/queue_length"
```

### Step 12: Setting Up the Horizontal Pod Autoscaler (HPA)

Set up the HPA using the following command:

```bash
kubectl apply -f ./k8s/hpa.yaml
```

Check the HPA details:

```bash
kubectl describe hpa orchestrator-hpa
```

Check the replication actions in the deployment:

```bash
kubectl describe deploy orchestrator-deploy
```

---

Follow these steps sequentially to deploy and manage your Java Spring Boot application effectively!
