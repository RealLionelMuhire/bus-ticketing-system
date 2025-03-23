package com.bus.ticketing.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketingSystemException extends RuntimeException {

    private String code;
    private String message;

    public TicketingSystemException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}