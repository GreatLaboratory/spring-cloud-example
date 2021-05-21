package com.example.orderservice.dto.create;

import lombok.Data;

import java.util.Date;

@Data
public class OrderCreateResponseDto {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

    private Date createdAt;

}
