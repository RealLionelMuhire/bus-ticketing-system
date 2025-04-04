package com.bus.ticketing.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CardTopUpResponse {
    private String message;
    private String transactionId;
    private String cardNumber;
    private BigDecimal newBalance;
}