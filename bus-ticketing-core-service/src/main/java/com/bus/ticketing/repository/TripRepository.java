package com.bus.ticketing.repository;

import com.bus.ticketing.model.TripStatus;
import com.bus.ticketing.model.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<TripEntity, Long> {
    Optional<TripEntity> findByTripNumber(String tripNumber);
    List<TripEntity> findByRouteIdAndDepartureTimeBetween(Long routeId, LocalDateTime start, LocalDateTime end);
    List<TripEntity> findByBusId(Long busId);
    List<TripEntity> findByStatus(TripStatus status);
}