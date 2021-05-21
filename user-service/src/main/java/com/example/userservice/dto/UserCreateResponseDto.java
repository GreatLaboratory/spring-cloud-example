package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserCreateResponseDto {

    private String  email;

    private String  name;

    private String  userId;
}
