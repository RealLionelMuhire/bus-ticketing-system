package com.bus.ticketing.exception;

public class InvalidBookingException extends TicketingSystemException {

    public InvalidBookingException(String message) {
        super("TICKETING-SERVICE-1002", message);
    }
}