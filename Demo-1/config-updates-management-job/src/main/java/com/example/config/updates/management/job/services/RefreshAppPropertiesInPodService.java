package com.example.config.updates.management.job.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@Service
public class RefreshAppPropertiesInPodService {

    private final RestTemplate restTemplate;

    public RefreshAppPropertiesInPodService() {
        this.restTemplate = new RestTemplate();
    }

    public void refreshAppProperties(final String configMapData, final Map<String, String> podRefreshEndpoints,
                                     final Map<String, String> podMountedPropertiesFileData) {
        podRefreshEndpoints.keySet().stream().forEach(podName -> {

            if (podMountedPropertiesFileData.get(podName).equals(configMapData)) {
                log.info("==========================================================================================");
                log.info("[SUCCESS] :: Content of Config File Mounted In Pod Matches with the Updated ConfigMap.");
                log.info("[PROCESSING] :: Invoking the actuator refresh endpoint for - pod name = {}", podName);
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
                    ResponseEntity<String> apiResponse = restTemplate.exchange(podRefreshEndpoints.get(podName), HttpMethod.POST,
                            new HttpEntity<>("", headers), String.class);
                    log.info("[COMPLETED] :: Actuator refresh API Response Code = {} and Response Body = {}",
                            apiResponse.getStatusCode(), apiResponse.getBody());
                } catch (Exception e) {
                    log.error("[SKIPPING] :: Actuator refresh API invocation incomplete for - pod name = {}", podName, e);
                }
                log.info("==========================================================================================\n\n");
            } else {
                log.warn("******************************************************************************************");
                log.warn("[FAILED] :: Content of Config File Mounted In Pod Does Not Match with the Updated ConfigMap.");
                log.debug("ConfigMap Data: \n{}", configMapData);
                log.debug("Pod File Data:  \n{}", podMountedPropertiesFileData.get(podName));
                log.warn("[SKIPPING] :: Actuator refresh API invocation incomplete for - pod name = {}", podName);
                log.warn("******************************************************************************************\n\n");
            }
        });
    }
}


