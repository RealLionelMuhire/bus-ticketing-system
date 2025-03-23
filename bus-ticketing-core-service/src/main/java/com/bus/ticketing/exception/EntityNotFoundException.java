package com.bus.ticketing.exception;

public class EntityNotFoundException extends TicketingSystemException {

    public EntityNotFoundException(String message) {
        super("TICKETING-SERVICE-1000", message);
    }
}