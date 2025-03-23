package com.bus.ticketing.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Company {
    private Long id;
    private String name;
    private String registrationNumber;
    private String contactEmail;
    private String contactPhone;
    private List<Bus> buses;
}