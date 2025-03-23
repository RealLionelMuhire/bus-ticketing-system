package com.bus.ticketing.model.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private Long tripId;
    private Long passengerId;
    private Long agentId; // Optional
    private Integer seatNumber;
    private String paymentMethod; // "CARD_TAP", "USSD", "AGENT", "CARD_ONLINE", "CASH"
    private String cardNumber; // If paying with card
    private String ussdCode; // If paying with USSD
    private String paymentReference; // Any reference number
    private String boardingStop; // The stop where passenger boards
    private String alightingStop; // The stop where passenger alights
}