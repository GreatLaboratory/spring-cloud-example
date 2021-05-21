package com.example.orderservice.service;

import com.example.orderservice.dto.create.OrderCreateRequestDto;
import com.example.orderservice.dto.create.OrderCreateResponseDto;
import com.example.orderservice.dto.read.OrderReadResponseDto;

import java.util.List;

public interface OrderService {
    OrderCreateResponseDto createOrder(OrderCreateRequestDto orderCreateRequestDto, String userId);
    OrderReadResponseDto getOrderByOrderId(String orderId);
    List<OrderReadResponseDto> getOrdersByUserId(String userId);
}
