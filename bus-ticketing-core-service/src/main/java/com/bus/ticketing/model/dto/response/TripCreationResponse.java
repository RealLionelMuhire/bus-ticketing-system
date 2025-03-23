package com.bus.ticketing.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TripCreationResponse {
    private String message;
    private String tripNumber;
}