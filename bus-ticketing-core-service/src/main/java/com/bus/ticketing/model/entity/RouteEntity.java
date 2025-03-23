package com.bus.ticketing.model.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bus_ticketing_route")
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String routeName;
    private String startLocation;
    private String endLocation;
    private Double distance;
    private Integer estimatedDuration; // in minutes

    @ElementCollection
    private List<String> stops;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    private List<TripEntity> trips;
}