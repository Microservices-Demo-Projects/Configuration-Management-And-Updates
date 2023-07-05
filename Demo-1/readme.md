# Microservice-One

## Command to Build and Run Docker Image

```shell 
docker build -t config-management-and-updates-demo-eg-1-ms-1:v1.0.0 .
```

```shell 
docker run -d -p 8080:80 config-management-and-updates-demo-eg-1-ms-1:v1.0.0
```

## Command to Publish Docker Image

### Re-tag the image with registry-host/registry-name/image-name:tag
```shell 
docker tag config-management-and-updates-demo-eg-1-ms-1:v1.0.0 sriramponangi/config-management-and-updates-demo-eg-1-ms-1:v1.0.0
```

### Login to the appropriate docker image registry
```shell 
docker logout
```

```shell 
docker login
```
### Push the docker image to registry
```shell 
docker push sriramponangi/config-management-and-updates-demo-eg-1-ms-1:v1.0.0
```

