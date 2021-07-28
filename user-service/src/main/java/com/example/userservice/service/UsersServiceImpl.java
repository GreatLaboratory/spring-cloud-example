package com.example.userservice.service;

import com.example.userservice.dto.create.UserCreateRequestDto;
import com.example.userservice.dto.create.UserCreateResponseDto;
import com.example.userservice.dto.read.OrderReadResponseDto;
import com.example.userservice.dto.read.UserReadResponseDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder encoder;
    private final RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당하는 이메일의 사용자가 존재하지 않습니다. email =" + email));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserCreateResponseDto createUser(UserCreateRequestDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPwd(encoder.encode(userDto.getPwd()));

        usersRepository.save(userEntity);

        UserCreateResponseDto userCreateResponseDto = modelMapper.map(userEntity, UserCreateResponseDto.class);
        return userCreateResponseDto;
    }

    @Override
    public UserReadResponseDto getUserByUserId(String userId) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = usersRepository.findByUserId(userId).orElseThrow(() ->
                new UsernameNotFoundException("해당하는 아이디의 사용자가 존재하지 않습니다. user id=" + userId)
        );
        UserReadResponseDto userReadResponseDto = modelMapper.map(userEntity, UserReadResponseDto.class);

        // 1. Using RestTemplate
        String orderUrl = String.format("http://127.0.0.1:8000/order-service/%s/orders", userId);
        ResponseEntity<List<OrderReadResponseDto>> orderResponseList = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<OrderReadResponseDto>>() {
                });
        List<OrderReadResponseDto> orderList = orderResponseList.getBody();

        // 2. Using FeignClient

        userReadResponseDto.setOrderList(orderList);

        return userReadResponseDto;
    }

    @Override
    public UserReadResponseDto getUserByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = usersRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당하는 이메일의 사용자가 존재하지 않습니다. email=" + email));
        UserReadResponseDto userReadResponseDto = modelMapper.map(userEntity, UserReadResponseDto.class);

        List<OrderReadResponseDto> orderList = new ArrayList<>();
        userReadResponseDto.setOrderList(orderList);

        return userReadResponseDto;
    }

    @Override
    public List<UserReadResponseDto> getUsers() {
        List<UserReadResponseDto> returnUserDtoList = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<UserEntity> userEntityList = usersRepository.findAll();
        List<OrderReadResponseDto> orderList = new ArrayList<>();

        userEntityList.forEach(userEntity -> {
            UserReadResponseDto userReadResponseDto = modelMapper.map(userEntity, UserReadResponseDto.class);
            userReadResponseDto.setOrderList(orderList);
            returnUserDtoList.add(userReadResponseDto);
        });

        return returnUserDtoList;
    }
}
