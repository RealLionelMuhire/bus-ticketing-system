package com.bus.ticketing.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Bus {
    private Long id;
    private String registrationNumber;
    private String model;
    private Integer capacity;
    private String status;
    private Company company;
    private User driver;
    private List<Trip> trips;
}