package com.bus.ticketing.exception;

public class InsufficientFundsException extends TicketingSystemException {

    public InsufficientFundsException(String message) {
        super("TICKETING-SERVICE-1001", message);
    }
}