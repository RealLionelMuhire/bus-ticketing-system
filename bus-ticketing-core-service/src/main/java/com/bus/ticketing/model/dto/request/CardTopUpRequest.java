package com.bus.ticketing.model.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardTopUpRequest {
    private String cardNumber;
    private BigDecimal amount;
}