package com.bus.ticketing.repository;

import com.bus.ticketing.model.entity.BusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<BusEntity, Long> {
    Optional<BusEntity> findByRegistrationNumber(String registrationNumber);
    List<BusEntity> findByCompanyId(Long companyId);
}