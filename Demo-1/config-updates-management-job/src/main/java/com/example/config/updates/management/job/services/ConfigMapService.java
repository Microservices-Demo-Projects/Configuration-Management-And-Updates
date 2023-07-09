package com.example.config.updates.management.job.services;


import com.example.config.updates.management.job.properties.ConfigMapProperties;
import com.example.config.updates.management.job.properties.PodProperties;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConfigMapService {

    private final KubernetesClient k8sClient;
    private final ConfigMapProperties configMapProperties;
    private final PodProperties podProperties;

    public ConfigMapService(KubernetesClient k8sClient, ConfigMapProperties configMapProperties,
                            PodProperties podProperties) {
        this.k8sClient = k8sClient;
        this.configMapProperties = configMapProperties;
        this.podProperties = podProperties;
    }

    public String getConfigMapData() {
        log.trace("ConfigMap Properties: {}", configMapProperties);
        ConfigMap configMap = k8sClient.configMaps()
                .inNamespace(configMapProperties.getNamespace())
                .withName(configMapProperties.getName())
                .get();

        log.trace("ConfigMap: {}.{} \n{}", configMapProperties.getName(), configMapProperties.getName(), configMap);
        String requiredData = configMap.getData().get(configMapProperties.getConfigMapDataKey());
        log.trace("ConfigMap Key: {} Data:\n{}", configMapProperties.getConfigMapDataKey(), requiredData);

        return requiredData;
    }
}
