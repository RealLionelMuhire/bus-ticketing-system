package com.bus.ticketing.model.entity;

import com.bus.ticketing.model.TripStatus;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bus_ticketing_trip")
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tripNumber;
    private LocalDateTime departureTime;
    private LocalDateTime estimatedArrivalTime;
    private Integer availableSeats;
    private Double price;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private RouteEntity route;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private BusEntity bus;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    private List<TicketEntity> tickets;
}