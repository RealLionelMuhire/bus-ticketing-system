package com.bus.ticketing.exception;

public class PaymentProcessingException extends TicketingSystemException {

    public PaymentProcessingException(String message) {
        super("TICKETING-SERVICE-1003", message);
    }
}