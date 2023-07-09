package com.example.config.updates.management.job.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)//, makeFinal=true
@ConfigurationProperties(prefix = "config-map-service")
public class ConfigMapProperties {
    String namespace;
    String name;
    String configMapDataKey;
    String externalPropertyFilePath;
}
