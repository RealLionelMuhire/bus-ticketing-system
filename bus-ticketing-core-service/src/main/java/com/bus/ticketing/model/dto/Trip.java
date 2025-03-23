package com.bus.ticketing.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Trip {
    private Long id;
    private String tripNumber;
    private LocalDateTime departureTime;
    private LocalDateTime estimatedArrivalTime;
    private Integer availableSeats;
    private Double price;
    private String status;
    private Route route;
    private Bus bus;
    private List<Ticket> tickets;
}