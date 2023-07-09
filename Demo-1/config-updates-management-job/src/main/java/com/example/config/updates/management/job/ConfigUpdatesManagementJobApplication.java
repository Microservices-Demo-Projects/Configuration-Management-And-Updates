package com.example.config.updates.management.job;

import com.example.config.updates.management.job.services.ConfigMapService;
import com.example.config.updates.management.job.services.PodService;
import com.example.config.updates.management.job.services.RefreshAppPropertiesInPodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@Slf4j
@SpringBootApplication
public class ConfigUpdatesManagementJobApplication implements CommandLineRunner {

    private final ConfigMapService configMapService;
    private final PodService podService;
    private final RefreshAppPropertiesInPodService refreshAppPropertiesInPodService;

    public ConfigUpdatesManagementJobApplication(ConfigMapService configMapService, PodService podService,
                                                 RefreshAppPropertiesInPodService refreshAppPropertiesInPodService) {
        this.configMapService = configMapService;
        this.podService = podService;
        this.refreshAppPropertiesInPodService = refreshAppPropertiesInPodService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigUpdatesManagementJobApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
		String configMapData = configMapService.getConfigMapData();
        Map<String, String> podRefreshEndpoints = podService.getPodsRefreshEndpointList();
        Map<String, String> podMountedPropertiesFileData = podService.getConfigFileInPods();
        refreshAppPropertiesInPodService.refreshAppProperties(configMapData, podRefreshEndpoints,
                podMountedPropertiesFileData);
    }
}
