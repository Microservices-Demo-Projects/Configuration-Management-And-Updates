# Demo Project: Configuration Management And Updates

## Demo-1: External Property Management and Dynamic Propert Refresh(i.e., without restarting the application) using Kubernetes Config Map
### Description
This demo aims to create one spring-boot application that can read/overwrite the properties from an external file. 
In the demo, the external properties file is a YAML file generated using the data defined in a Kubernetes config map.

Also, we allow the application to refresh/reload the property values based on the changes to the properties in the
config map without restarting the application i.e., dynamic property refresh.

### High-Level Design


---

## Demo-2: Secrets Management with Dynamic Secret Generation and Dynamic Secrets Refresh with Hashicorp Vault.
### Description
Secret Management in SpringBoot Microservice with Valut and Kubernetes
    - https://cloud.spring.io/spring-cloud-vault/reference/html/#vault.config.authentication.kubernetes

    
### High-Level Design

