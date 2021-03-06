package com.example.userservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping({"/first-service", "/second-service"})
public class UserController {

    @Value("${server.port}")
    private String port;

    private String getProfile() {
        if (port.equals("9090")) {
            return "first-service";
        } else {
            return "second-service";
        }
    }

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        return "Welcome " + request.getServerPort();
    }

    @GetMapping("/message1")
    public String message1(@RequestHeader("first-request") String firstHeader) {
        return "Message Header : " + firstHeader;
    }

    @GetMapping("/message2")
    public String message2(@RequestHeader("second-request") String secondHeader) {
        return "Message Header : " + secondHeader;
    }

}
