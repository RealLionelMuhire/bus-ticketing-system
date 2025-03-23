package com.bus.ticketing.service;

import com.bus.ticketing.exception.EntityNotFoundException;
import com.bus.ticketing.exception.InvalidBookingException;
import com.bus.ticketing.exception.SeatUnavailableException;
import com.bus.ticketing.model.PaymentMethod;
import com.bus.ticketing.model.TicketStatus;
import com.bus.ticketing.model.TripStatus;
import com.bus.ticketing.model.dto.Ticket;
import com.bus.ticketing.model.dto.request.BookingRequest;
import com.bus.ticketing.model.dto.response.BookingResponse;
import com.bus.ticketing.model.entity.CardEntity;
import com.bus.ticketing.model.entity.TicketEntity;
import com.bus.ticketing.model.entity.TripEntity;
import com.bus.ticketing.model.entity.UserEntity;
import com.bus.ticketing.model.mapper.TicketMapper;
import com.bus.ticketing.repository.CardRepository;
import com.bus.ticketing.repository.TicketRepository;
import com.bus.ticketing.repository.TripRepository;
import com.bus.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final TicketMapper ticketMapper = new TicketMapper();

    public Ticket getTicketByNumber(String ticketNumber) {
        TicketEntity ticket = ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new RuntimeException("Ticket not found with number: " + ticketNumber));
        return ticketMapper.convertToDto(ticket);
    }

    public List<Ticket> getTicketsByTrip(Long tripId) {
        return ticketMapper.convertToDtoList(ticketRepository.findByTripId(tripId));
    }

    public List<Ticket> getTicketsByPassenger(Long passengerId) {
        return ticketMapper.convertToDtoList(ticketRepository.findByPassengerId(passengerId));
    }

    @Transactional
    public BookingResponse bookTicket(BookingRequest request) {
        // Validate trip exists and is available for booking
        TripEntity trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new EntityNotFoundException("Trip not found with ID: " + request.getTripId()));

        if (trip.getStatus() != TripStatus.SCHEDULED) {
            throw new InvalidBookingException("Trip is not available for booking");
        }

        if (trip.getAvailableSeats() <= 0) {
            throw new SeatUnavailableException("No seats available for this trip");
        }

        // Validate boarding and alighting stops
        validateStops(trip, request.getBoardingStop(), request.getAlightingStop());

        // Check if seat is already booked
        boolean seatTaken = ticketRepository.findByTripIdAndStatus(trip.getId(), TicketStatus.BOOKED)
                .stream()
                .anyMatch(ticket -> ticket.getSeatNumber().equals(request.getSeatNumber()));

        if (seatTaken) {
            throw new SeatUnavailableException("Seat " + request.getSeatNumber() + " is already taken");
        }

        // Get passenger and agent (if applicable)
        UserEntity passenger = userRepository.findById(request.getPassengerId())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with ID: " + request.getPassengerId()));

        UserEntity agent = null;
        if (request.getAgentId() != null) {
            agent = userRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + request.getAgentId()));
        }

        // Convert payment method string to enum
        PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getPaymentMethod());

        // Calculate price based on full route or partial route
        BigDecimal ticketPrice = calculateTicketPrice(trip, request.getBoardingStop(), request.getAlightingStop());

        // Process payment based on method
        String paymentReference = processPayment(paymentMethod, request, ticketPrice);

        // Create ticket
        TicketEntity ticket = TicketEntity.builder()
                .ticketNumber(generateTicketNumber())
                .price(ticketPrice)
                .seatNumber(request.getSeatNumber())
                .purchaseTime(LocalDateTime.now())
                .status(TicketStatus.BOOKED)
                .trip(trip)
                .passenger(passenger)
                .agent(agent)
                .boardingStop(request.getBoardingStop())
                .alightingStop(request.getAlightingStop())
                .paymentMethod(paymentMethod)
                .paymentReference(paymentReference)
                .build();

        ticket = ticketRepository.save(ticket);

        // Update available seats
        trip.setAvailableSeats(trip.getAvailableSeats() - 1);
        tripRepository.save(trip);

        return BookingResponse.builder()
                .message("Ticket booked successfully")
                .ticketNumber(ticket.getTicketNumber())
                .tripNumber(trip.getTripNumber())
                .departureTime(trip.getDepartureTime())
                .route(trip.getRoute().getRouteName())
                .seatNumber(ticket.getSeatNumber())
                .price(ticket.getPrice())
                .boardingStop(ticket.getBoardingStop())
                .alightingStop(ticket.getAlightingStop())
                .paymentMethod(ticket.getPaymentMethod().name())
                .build();
    }

    private String processPayment(PaymentMethod paymentMethod, BookingRequest request, BigDecimal amount) {
        String paymentReference = request.getPaymentReference();

        switch(paymentMethod) {
            case CARD_TAP:
            case CARD_ONLINE:
                processCardPayment(request.getCardNumber(), amount);
                if (paymentReference == null) {
                    paymentReference = "CARD-" + UUID.randomUUID().toString().substring(0, 8);
                }
                break;

            case USSD:
                validateUssdPayment(request.getUssdCode(), amount);
                if (paymentReference == null) {
                    paymentReference = request.getUssdCode();
                }
                break;

            case AGENT:
                // For agent-assisted bookings, we'll use the agent's ID as part of the reference
                if (paymentReference == null && request.getAgentId() != null) {
                    paymentReference = "AGENT-" + request.getAgentId() + "-" +
                            UUID.randomUUID().toString().substring(0, 6);
                }
                break;

            case CASH:
                // For cash payments, generate a simple reference if not provided
                if (paymentReference == null) {
                    paymentReference = "CASH-" + UUID.randomUUID().toString().substring(0, 8);
                }
                break;

            default:
                throw new RuntimeException("Unsupported payment method: " + paymentMethod);
        }

        return paymentReference;
    }

    private void processCardPayment(String cardNumber, BigDecimal amount) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new RuntimeException("Card number is required for card payments");
        }

        CardEntity card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Card not found with number: " + cardNumber));

        if (card.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance on card. Required: " +
                    amount + ", Available: " + card.getBalance());
        }

        // Deduct balance
        card.setBalance(card.getBalance().subtract(amount));
        cardRepository.save(card);

        log.info("Card payment processed: Card {}, Amount {}", cardNumber, amount);
    }

    private void validateUssdPayment(String ussdCode, BigDecimal amount) {
        if (ussdCode == null || ussdCode.trim().isEmpty()) {
            throw new RuntimeException("USSD code is required for USSD payments");
        }

        // This would typically integrate with a telecom API
        // For now, we'll implement a simple validation based on code format
        if (ussdCode.length() < 8 || !ussdCode.matches("^[0-9*#]+$")) {
            throw new RuntimeException("Invalid USSD payment code format");
        }

        // In a real implementation, this would verify the payment with the telecom provider
        // and might also check if the amount matches what was paid via USSD
        log.info("USSD payment code validated: Code {}, Amount {}", ussdCode, amount);
    }

    private BigDecimal calculateTicketPrice(TripEntity trip, String boardingStop, String alightingStop) {
        // In a real implementation, this would calculate the price based on the distance between stops
        // For simplicity, we'll use a percentage of the full trip price based on the stops

        if (boardingStop == null || alightingStop == null) {
            // If no specific stops are provided, charge full price
            return BigDecimal.valueOf(trip.getPrice());
        }

        // Here we would ideally look up the stops in the route and calculate distance
        // For now, we'll just provide a simple implementation that returns the full price
        // A real implementation might adjust the price based on distance between stops

        return BigDecimal.valueOf(trip.getPrice());
    }

    private void validateStops(TripEntity trip, String boardingStop, String alightingStop) {
        // In a real implementation, this would check if the provided stops are valid for this trip
        if (boardingStop == null || alightingStop == null) {
            return; // Using default stops (full route)
        }

        // Get all stops for this route and verify the boarding and alighting stops are valid
        // In this simplified version, we just log the stops
        log.info("Booking from {} to {} on trip {}", boardingStop, alightingStop, trip.getTripNumber());

        // Check if the boarding stop comes before the alighting stop in the sequence
        // This would require the TripStopEntity to be properly set up
    }

    private String generateTicketNumber() {
        return "TKT" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}