package com.example.userservice.dto.read;

import lombok.Data;

import java.util.Date;

@Data
public class OrderReadResponseDto {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private Date createdAt;

    private String orderId;
}
