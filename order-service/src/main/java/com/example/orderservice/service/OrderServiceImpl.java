package com.example.orderservice.service;

import com.example.orderservice.dto.create.OrderCreateRequestDto;
import com.example.orderservice.dto.create.OrderCreateResponseDto;
import com.example.orderservice.dto.read.OrderReadResponseDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto, String userId) {
        requestDto.setUserId(userId);
        requestDto.setOrderId(UUID.randomUUID().toString());
        requestDto.setTotalPrice(requestDto.getQty() * requestDto.getUnitPrice());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderEntity orderEntity = modelMapper.map(requestDto, OrderEntity.class);
        orderRepository.save(orderEntity);

        OrderCreateResponseDto orderCreateResponseDto = modelMapper.map(orderEntity, OrderCreateResponseDto.class);
        return orderCreateResponseDto;
    }

    @Override
    public OrderReadResponseDto getOrderByOrderId(String orderId) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당하는 아이디의 주문이 존재하지 않습니다. order id=" + orderId)
        );

        OrderReadResponseDto orderReadResponseDto = modelMapper.map(orderEntity, OrderReadResponseDto.class);
        return orderReadResponseDto;
    }

    @Override
    public List<OrderReadResponseDto> getOrdersByUserId(String userId) {
        List<OrderReadResponseDto> orderReadResponseDtoList = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<OrderEntity> orderEntityList = orderRepository.findByUserId(userId);
        orderEntityList.forEach(orderEntity -> {
            OrderReadResponseDto orderReadResponseDto = modelMapper.map(orderEntity, OrderReadResponseDto.class);
            orderReadResponseDtoList.add(orderReadResponseDto);
        });

        return orderReadResponseDtoList;
    }
}
