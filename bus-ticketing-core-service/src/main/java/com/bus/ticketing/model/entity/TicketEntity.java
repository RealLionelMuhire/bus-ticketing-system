package com.bus.ticketing.model.entity;

import com.bus.ticketing.model.PaymentMethod;
import com.bus.ticketing.model.TicketStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@Table(name = "bus_ticketing_ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketNumber;
    private BigDecimal price;
    private Integer seatNumber;
    private LocalDateTime purchaseTime;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private TripEntity trip;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private UserEntity passenger;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private UserEntity agent; // Can be null if purchased directly by passenger

    @Column(name = "boarding_stop")
    private String boardingStop;

    @Column(name = "alighting_stop")
    private String alightingStop;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "payment_reference")
    private String paymentReference;
}