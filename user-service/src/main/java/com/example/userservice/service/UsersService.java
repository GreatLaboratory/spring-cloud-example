package com.example.userservice.service;

import com.example.userservice.dto.UserCreateRequestDto;
import com.example.userservice.dto.UserCreateResponseDto;

public interface UsersService {
    public UserCreateResponseDto createUser(UserCreateRequestDto userCreateRequestDto);
}
