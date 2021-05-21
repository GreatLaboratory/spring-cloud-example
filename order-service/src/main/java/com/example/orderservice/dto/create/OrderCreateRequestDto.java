package com.example.orderservice.dto.create;

import lombok.Data;

@Data
public class OrderCreateRequestDto {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String userId;
    private String orderId;

}
