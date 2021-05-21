package com.example.orderservice.dto.read;

import lombok.Data;

import java.util.Date;

@Data
public class OrderReadResponseDto {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

    private Date createdAt;

}
