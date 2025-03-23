package com.bus.ticketing.service;

import com.bus.ticketing.model.CardStatus;
import com.bus.ticketing.model.dto.Card;
import com.bus.ticketing.model.dto.request.CardTopUpRequest;
import com.bus.ticketing.model.dto.response.CardTopUpResponse;
import com.bus.ticketing.model.entity.CardEntity;
import com.bus.ticketing.model.entity.UserEntity;
import com.bus.ticketing.model.mapper.CardMapper;
import com.bus.ticketing.repository.CardRepository;
import com.bus.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper = new CardMapper();

    public Card readCardByNumber(String cardNumber) {
        CardEntity cardEntity = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Card not found with number: " + cardNumber));
        return cardMapper.convertToDto(cardEntity);
    }

    public List<Card> readCardsByUserId(Long userId) {
        return cardMapper.convertToDtoList(cardRepository.findByUserId(userId));
    }

    @Transactional
    public Card createCard(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        CardEntity card = new CardEntity();
        card.setCardNumber(generateCardNumber());
        card.setBalance(BigDecimal.ZERO);
        card.setIssueDate(LocalDateTime.now());
        card.setExpiryDate(LocalDateTime.now().plusYears(2));
        card.setStatus(CardStatus.ACTIVE);
        card.setUser(user);

        card = cardRepository.save(card);
        return cardMapper.convertToDto(card);
    }

    @Transactional
    public CardTopUpResponse topUpCard(CardTopUpRequest request) {
        CardEntity card = cardRepository.findByCardNumber(request.getCardNumber())
                .orElseThrow(() -> new RuntimeException("Card not found with number: " + request.getCardNumber()));

        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new RuntimeException("Card is not active");
        }

        card.setBalance(card.getBalance().add(request.getAmount()));
        card = cardRepository.save(card);

        String transactionId = UUID.randomUUID().toString();

        return CardTopUpResponse.builder()
                .message("Card topped up successfully")
                .transactionId(transactionId)
                .cardNumber(card.getCardNumber())
                .newBalance(card.getBalance())
                .build();
    }

    private String generateCardNumber() {
        return "BT" + System.currentTimeMillis();
    }
}