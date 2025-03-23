package com.bus.ticketing.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Route {
    private Long id;
    private String routeName;
    private String startLocation;
    private String endLocation;
    private Double distance;
    private Integer estimatedDuration;
    private List<String> stops;
    private List<Trip> trips;
}