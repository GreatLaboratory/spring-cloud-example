package com.example.userservice.service;

import com.example.userservice.dto.create.UserCreateRequestDto;
import com.example.userservice.dto.create.UserCreateResponseDto;
import com.example.userservice.dto.read.UserReadResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService {
    public UserCreateResponseDto createUser(UserCreateRequestDto userCreateRequestDto);
    public UserReadResponseDto getUserByUserId(String userId);
    public UserReadResponseDto getUserByEmail(String email);
    public List<UserReadResponseDto> getUsers();
}
