package com.example.userservice.controller;

import com.example.userservice.dto.UserCreateRequestDto;
import com.example.userservice.dto.UserCreateResponseDto;
import com.example.userservice.service.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UsersController {

    private final Environment env;
    private final UsersServiceImpl usersService;

    @GetMapping("/health_check")
    public String healthCheck() {
        return "It's working in user service!!";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody UserCreateRequestDto userDto) {
        UserCreateResponseDto userCreateResponseDto = usersService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userCreateResponseDto);
    }
}
