package com.demo.microservice.one.controllers;

import com.demo.microservice.one.configs.AppConfigs;
import com.demo.microservice.one.models.HelloMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final AppConfigs appConfigs;

    public HelloController(AppConfigs appConfigs) {
        this.appConfigs = appConfigs;
    }

    @GetMapping("/hello")
    private HelloMessage getMessage() {
        return new HelloMessage(appConfigs.getGreetingMessage());
    }
}
