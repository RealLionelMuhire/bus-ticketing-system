package com.bus.ticketing.controller;

import com.bus.ticketing.model.dto.request.BookingRequest;
import com.bus.ticketing.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/ticket/{ticketNumber}")
    public ResponseEntity getTicket(@PathVariable("ticketNumber") String ticketNumber) {
        log.info("Reading ticket by number: {}", ticketNumber);
        return ResponseEntity.ok(bookingService.getTicketByNumber(ticketNumber));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity getTicketsByTrip(@PathVariable("tripId") Long tripId) {
        log.info("Reading tickets for trip with ID: {}", tripId);
        return ResponseEntity.ok(bookingService.getTicketsByTrip(tripId));
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity getTicketsByPassenger(@PathVariable("passengerId") Long passengerId) {
        log.info("Reading tickets for passenger with ID: {}", passengerId);
        return ResponseEntity.ok(bookingService.getTicketsByPassenger(passengerId));
    }

    @PostMapping
    public ResponseEntity bookTicket(@RequestBody BookingRequest request) {
        log.info("Booking new ticket: {}", request);
        return ResponseEntity.ok(bookingService.bookTicket(request));
    }
}