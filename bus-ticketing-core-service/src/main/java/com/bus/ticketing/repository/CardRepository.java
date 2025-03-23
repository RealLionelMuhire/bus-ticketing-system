package com.bus.ticketing.repository;

import com.bus.ticketing.model.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByCardNumber(String cardNumber);
    List<CardEntity> findByUserId(Long userId);
}