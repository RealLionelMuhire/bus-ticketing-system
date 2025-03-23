package com.bus.ticketing.exception;

public class SeatUnavailableException extends TicketingSystemException {

    public SeatUnavailableException(String message) {
        super("TICKETING-SERVICE-1004", message);
    }
}