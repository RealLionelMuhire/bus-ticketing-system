package com.bus.ticketing.repository;

import com.bus.ticketing.model.TicketStatus;
import com.bus.ticketing.model.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    Optional<TicketEntity> findByTicketNumber(String ticketNumber);
    List<TicketEntity> findByTripId(Long tripId);
    List<TicketEntity> findByPassengerId(Long passengerId);
    List<TicketEntity> findByTripIdAndStatus(Long tripId, TicketStatus status);
}