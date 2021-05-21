package com.example.userservice.dto.read;

import lombok.Data;

import java.util.List;

@Data
public class UserReadResponseDto {

    private Long id;

    private String  email;

    private String  name;

    private String  userId;

    private List<OrderReadResponseDto> orderList;

}
