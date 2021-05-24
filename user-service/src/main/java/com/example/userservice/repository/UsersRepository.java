package com.example.userservice.repository;

import com.example.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByUserId(String userId);
    public Optional<UserEntity> findByEmail(String email);
}
