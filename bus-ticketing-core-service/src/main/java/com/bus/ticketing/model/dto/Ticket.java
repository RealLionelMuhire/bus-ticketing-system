package com.bus.ticketing.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Ticket {
    private Long id;
    private String ticketNumber;
    private BigDecimal price;
    private Integer seatNumber;
    private LocalDateTime purchaseTime;
    private String status;
    private String paymentMethod; // Add this field if missing
    private String paymentReference; // Possibly add this too
    private String boardingStop; // And this
    private String alightingStop; // And this
    private Trip trip;
    private User passenger;
    private User agent;
}