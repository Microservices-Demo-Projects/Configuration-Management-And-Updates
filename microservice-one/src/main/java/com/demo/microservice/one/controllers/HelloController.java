package com.demo.microservice.one.controllers;

import com.demo.microservice.one.models.HelloMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    private HelloMessage getMessage() {
        return new HelloMessage("Hello World");
    }
}
