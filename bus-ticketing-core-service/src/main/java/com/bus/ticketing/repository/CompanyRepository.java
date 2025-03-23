package com.bus.ticketing.repository;

import com.bus.ticketing.model.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    Optional<CompanyEntity> findByRegistrationNumber(String registrationNumber);
}