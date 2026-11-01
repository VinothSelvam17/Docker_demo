package com.demo.dockerdemo.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public String getHelloMessage() {
        return "Hello, Jenkins CI/CD is working! Now, It's Changed Right....!";
    }

    public String getWelcomeMessage() {
        return "Welcome to Docker!";
    }

    public String getStatus() {
        return "Application is running";
    }
}
