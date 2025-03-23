package com.bus.ticketing.model.entity;

import lombok.Getter;
import lombok.Setter;
import com.bus.ticketing.model.BusStatus;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bus_ticketing_bus")
public class BusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registrationNumber;
    private String model;
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private BusStatus status;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private UserEntity driver;

    @OneToMany(mappedBy = "bus", fetch = FetchType.LAZY)
    private List<TripEntity> trips;
}