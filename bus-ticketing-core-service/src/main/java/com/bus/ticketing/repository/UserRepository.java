package com.bus.ticketing.repository;

import com.bus.ticketing.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdentificationNumber(String identificationNumber);
    Optional<UserEntity> findByEmail(String email);
}