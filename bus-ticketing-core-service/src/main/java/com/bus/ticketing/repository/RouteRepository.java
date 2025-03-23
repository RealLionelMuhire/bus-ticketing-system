package com.bus.ticketing.repository;

import com.bus.ticketing.model.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<RouteEntity, Long> {
    List<RouteEntity> findByStartLocationAndEndLocation(String startLocation, String endLocation);
}