package com.bus.ticketing.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class BookingResponse {
    private String message;
    private String ticketNumber;
    private String tripNumber;
    private LocalDateTime departureTime;
    private String route;
    private Integer seatNumber;
    private BigDecimal price;
    private String boardingStop;
    private String alightingStop;
    private String paymentMethod;
}