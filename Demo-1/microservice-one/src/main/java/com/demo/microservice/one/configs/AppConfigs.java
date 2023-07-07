package com.demo.microservice.one.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@RefreshScope
@Configuration
public class AppConfigs {

    @Value("${demo.greeting.message:Hello World from local!}")
    private String greetingMessage;
}
