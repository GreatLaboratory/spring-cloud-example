package com.example.userservice.service;

import com.example.userservice.dto.UserCreateRequestDto;
import com.example.userservice.dto.UserCreateResponseDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;

    @Override
    public UserCreateResponseDto createUser(UserCreateRequestDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPwd("");

        usersRepository.save(userEntity);

        UserCreateResponseDto userCreateResponseDto = modelMapper.map(userEntity, UserCreateResponseDto.class);
        return userCreateResponseDto;
    }
}
