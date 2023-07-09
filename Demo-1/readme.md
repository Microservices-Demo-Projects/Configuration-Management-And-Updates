# Demo-1:
> Platform versions used in this demo:
- Docker - v23.0.5
- Kubernebtes - v1.25.9
- Java - 20
- Springboot - 3.1.1



# Docker Commands

## Microservice-One

#### Command to Build Docker Image

```shell 
docker build -t config-management-and-updates-demo-1-ms-1:v1.0.0 .
```

#### Command to Build Docker Image
```shell 
docker run -d --expose=9090 -p 8080:8080 -p 9090:9090 --name config-management-and-updates-demo-1-ms-1 config-management-and-updates-demo-1-ms-1:v1.0.0
```

#### Commands to Publish Docker Image

###### Re-tag the image with registry-host/registry-name/image-name:tag
```shell 
docker tag config-management-and-updates-demo-1-ms-1:v1.0.0 sriramponangi/config-management-and-updates-demo-1-ms-1:v1.0.0
```

###### Login to the appropriate docker image registry
```shell 
docker logout
```

```shell 
docker login
```

###### Push the docker image to registry
```shell 
docker push sriramponangi/config-management-and-updates-demo-1-ms-1:v1.0.0
```







## Config-Updates-Management-Job

#### Command to Build Docker Image

```shell 
docker build -t sriramponangi/config-management-and-updates-demo-1-job-1:v1.0.0 .
```

###### Push the docker image to registry
```shell 
docker push sriramponangi/config-management-and-updates-demo-1-job-1:v1.0.0
```






# Kubernetes Commands
## Microservice-One

> #### Command to create Kubernetes objects required to run the pod with external properties
```shell 
kubectl apply -f microservice-one-k8s.yml
```

> #### Command to delete all the Kubernetes objects required to run the pod with external properties
```shell 
kubectl delete -f microservice-one-k8s.yml
```

> #### Other Useful Commands to work with the Kubernetes objects
```shell 
kubectl get all
```

```shell 
kubectl get pods
```

```shell 
kubectl describe pods
```

```shell 
kubectl logs -f -p microservice-one-deployment-<pod-id>
```

```shell 
kubectl exec microservice-one-deployment-<pod-id> -it -- sh
```

> #### Updating the configs maps and refreshing application configs without restarting pods:

```shell 
kubectl get cm
```

```shell 
kubectl describe cm app-external-configs
```

```shell 
 kubectl edit cm app-external-configs
 ```

```shell 
kubectl apply -f config-updates-management-job-k8s.yml
```

```shell 
kubectl delete -f config-updates-management-job-k8s.yml
```

```shell 
 kubectl logs -f jobs/config-updates-management-job
 ```
```shell 
 kubectl delete jobs/config-updates-management-job
 ```


 :
    > Approach to invoke /actuato/refresh API on all pods under a service
        > Create a service account, roles and roleBindings to fetch list of all pod endpoint and ports;
          config maps and secrets data by accessing and API in the Kube-API server
        > login to each pod and check if the updated config-map data is refelected in the mounted file inside the pod.
        > Once all the podIPs and ports are available invoke the curl -X POST http://podId:port/actuator/refresh on each of the
        pod replicas managed under the service/deployment.
        > Write a script file with all these steps 
            > References: 
                > https://stackoverflow.com/questions/49612412/kubenetes-is-it-possible-to-hit-multiple-pods-with-a-single-request-in-kubernet
                > https://stackoverflow.com/a/65253583
        > Try creating this script as a kuberentes job.

    > kubectl get endpoints microservice-one  -o jsonpath="{.subsets[*].addresses[*].ip}" | xargs curl 
    > kubectl get endpoints microservice-one -o yaml