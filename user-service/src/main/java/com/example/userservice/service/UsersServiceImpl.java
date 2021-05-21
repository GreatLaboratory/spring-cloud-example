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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder encoder;

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

        UserEntity userEntity = usersRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("해당하는 아이디의 사용자가 존재하지 않습니다. user id=" + userId));
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
