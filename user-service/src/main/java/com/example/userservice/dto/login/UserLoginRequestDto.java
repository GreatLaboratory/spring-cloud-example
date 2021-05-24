package com.example.userservice.dto.login;

import lombok.Data;

@Data
public class UserLoginRequestDto {

    private String email;
    private String pwd;

}
