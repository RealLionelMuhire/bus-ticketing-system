package com.bus.ticketing.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Card {
    private Long id;
    private String cardNumber;
    private BigDecimal balance;
    private LocalDateTime issueDate;
    private LocalDateTime expiryDate;
    private String status;
    private User user;
}