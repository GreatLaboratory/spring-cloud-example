package com.example.userservice.service;

import com.example.userservice.dto.create.UserCreateRequestDto;
import com.example.userservice.dto.create.UserCreateResponseDto;
import com.example.userservice.dto.read.UserReadResponseDto;

import java.util.List;

public interface UsersService {
    public UserCreateResponseDto createUser(UserCreateRequestDto userCreateRequestDto);
    public UserReadResponseDto getUserByUserId(String userId);
    public List<UserReadResponseDto> getUsers();
}
