package com.example.userservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/first-service", "/second-service"})
public class UserController {

    @Value("${server.port}")
    private String port;

    private String getProfile() {
        if (port.equals("9001")) {
            return "first-service";
        } else {
            return "second-service";
        }
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome " + this.getProfile();
    }

}
