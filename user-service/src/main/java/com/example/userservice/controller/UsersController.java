package com.example.userservice.controller;

import com.example.userservice.dto.create.UserCreateRequestDto;
import com.example.userservice.dto.create.UserCreateResponseDto;
import com.example.userservice.dto.read.UserReadResponseDto;
import com.example.userservice.service.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UsersController {

    private final Environment env;
    private final UsersServiceImpl usersService;

    @GetMapping("/health_check")
    public String healthCheck() {
        return String.format("It's Working in User Servce"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", gateway ip=" + env.getProperty("gateway.ip")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @PostMapping("/users")
    public ResponseEntity<UserCreateResponseDto> createUser(@RequestBody UserCreateRequestDto userDto) {
        UserCreateResponseDto userCreateResponseDto = usersService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreateResponseDto);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserReadResponseDto> getUserById(@PathVariable String userId) {
        UserReadResponseDto userReadResponseDto = usersService.getUserByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userReadResponseDto);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserReadResponseDto>> getUsers() {
        List<UserReadResponseDto> userList = usersService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

}
