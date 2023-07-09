package com.example.config.updates.management.job.services;


import com.example.config.updates.management.job.properties.PodProperties;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.PodResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Service
public class PodService {

    private final KubernetesClient k8sClient;
    private final PodProperties podProperties;

    public PodService(KubernetesClient k8sClient, PodProperties podProperties) {
        this.k8sClient = k8sClient;
        this.podProperties = podProperties;
    }

    public Map<String, String> getPodsRefreshEndpointList() {
        log.trace("Pod Properties: {}", podProperties);

        PodList podList = k8sClient.pods()
                .inNamespace(podProperties.getNamespace())
                .withLabelSelector(podProperties.getLabelSelectors())
                .list();


        Map<String, String> podRefreshEndpoints = podList.getItems().stream().collect(Collectors.toMap(
                pod -> pod.getMetadata().getName(),
                pod -> {
                    String podIP = pod.getStatus().getPodIP();
                    podIP = podIP.replaceAll("\\.", "-");
                    return "http://" + podIP + "." + podProperties.getNamespace() + "." +
                            "pod" + "." + podProperties.getClusterDomain() + ":" +
                            podProperties.getManagementApiPort() + podProperties.getRefreshEndpoint();
                }
        ));



        //Pod DNS Pattern: <pod-ip>.<namespace>.pod.<cluster-domain>
        //Pod DNS Eg: 10-1-0-80.default.pod.cluster.local
        podRefreshEndpoints.forEach((podName, endpoint) -> {
            log.trace("Pod: {} Endpoint: {}\n", podName, endpoint);
        });

        return podRefreshEndpoints;
    }

    public Map<String, String> getConfigFileInPods() throws IOException {
        Stream<PodResource> podResourceStream = k8sClient.pods()
                .inNamespace(podProperties.getNamespace())
                .withLabelSelector(podProperties.getLabelSelectors())
                .resources();


        Map<String, String> podMountedPropertiesFileData = podResourceStream.collect(Collectors.toMap(
                podResource -> podResource.get().getMetadata().getName(),
                podResource -> {
                    InputStream inputStream = podResource.file(podProperties.getExternalPropertyFilePath()).read();
                    try {
                        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ));

        podMountedPropertiesFileData.forEach((podName, data) -> {
            log.trace("Pod: {} External Properties File Content: \n{}\n\n\n", podName, data);
        });

        return podMountedPropertiesFileData;
    }
}
