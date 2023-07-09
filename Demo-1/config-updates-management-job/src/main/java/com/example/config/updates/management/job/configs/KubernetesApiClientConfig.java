package com.example.config.updates.management.job.configs;


import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

@Configuration
public class KubernetesApiClientConfig {

    @Primary
    @Bean("Default")
    public KubernetesClient createKubernetesApiClient() throws IOException {
        return new KubernetesClientBuilder().build();
    }

}
