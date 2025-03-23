package com.bus.ticketing.model.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripCreationRequest {
    private Long routeId;
    private Long busId;
    private LocalDateTime departureTime;
    private BigDecimal price;
}