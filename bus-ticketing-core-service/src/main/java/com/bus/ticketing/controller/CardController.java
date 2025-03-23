package com.bus.ticketing.controller;

import com.bus.ticketing.model.dto.request.CardTopUpRequest;
import com.bus.ticketing.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/{cardNumber}")
    public ResponseEntity getCard(@PathVariable("cardNumber") String cardNumber) {
        log.info("Reading card by number: {}", cardNumber);
        return ResponseEntity.ok(cardService.readCardByNumber(cardNumber));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getCardsByUser(@PathVariable("userId") Long userId) {
        log.info("Reading cards for user with ID: {}", userId);
        return ResponseEntity.ok(cardService.readCardsByUserId(userId));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity createCard(@PathVariable("userId") Long userId) {
        log.info("Creating new card for user with ID: {}", userId);
        return ResponseEntity.ok(cardService.createCard(userId));
    }

    @PostMapping("/topup")
    public ResponseEntity topUpCard(@RequestBody CardTopUpRequest request) {
        log.info("Topping up card: {}", request);
        return ResponseEntity.ok(cardService.topUpCard(request));
    }
}