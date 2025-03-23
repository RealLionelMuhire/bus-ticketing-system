package com.bus.ticketing.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String identificationNumber;
    private String userType;
    private List<Card> cards;
}