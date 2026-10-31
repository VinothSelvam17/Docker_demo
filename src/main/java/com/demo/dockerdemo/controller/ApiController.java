package com.demo.dockerdemo.controller;

import com.demo.dockerdemo.dto.MessageResponse;
import com.demo.dockerdemo.dto.StatusResponse;
import com.demo.dockerdemo.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final MessageService messageService;

    public ApiController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/hello")
    public MessageResponse hello() {
        return new MessageResponse(messageService.getHelloMessage());
    }

    @GetMapping("/message")
    public MessageResponse message() {
        return new MessageResponse(messageService.getWelcomeMessage());
    }

    @GetMapping("/status")
    public StatusResponse status() {
        return new StatusResponse(messageService.getStatus());
    }
}
