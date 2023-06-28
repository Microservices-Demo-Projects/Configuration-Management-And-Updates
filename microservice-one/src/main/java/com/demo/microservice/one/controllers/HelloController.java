package com.demo.microservice.one.controllers;

import com.demo.microservice.one.models.HelloMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${demo.greeting.message:Hello World :D !}")
    private String greeterMessage;

    @GetMapping("/hello")
    private HelloMessage getMessage() {
        return new HelloMessage(greeterMessage);
    }
}
