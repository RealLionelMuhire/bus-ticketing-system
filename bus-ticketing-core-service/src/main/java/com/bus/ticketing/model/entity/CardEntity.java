package com.bus.ticketing.model.entity;

import com.bus.ticketing.model.CardStatus;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bus_ticketing_card")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;
    private BigDecimal balance;
    private LocalDateTime issueDate;
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}