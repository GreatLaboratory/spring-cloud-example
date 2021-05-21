package com.example.orderservice.controller;

import com.example.orderservice.dto.create.OrderCreateRequestDto;
import com.example.orderservice.dto.create.OrderCreateResponseDto;
import com.example.orderservice.dto.read.OrderReadResponseDto;
import com.example.orderservice.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/order-service")
@RestController
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderCreateResponseDto> createOrder(@RequestBody OrderCreateRequestDto requestDto, @PathVariable String userId) {
        OrderCreateResponseDto orderCreateResponseDto = orderService.createOrder(requestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderCreateResponseDto);
    };

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderReadResponseDto> createOrder(@PathVariable String orderId) {
        OrderReadResponseDto orderReadResponseDto = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderReadResponseDto);
    };

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderReadResponseDto>> getOrdersByUserId(@PathVariable String userId) {
        List<OrderReadResponseDto> orderReadResponseDtoList = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderReadResponseDtoList);
    };

}
