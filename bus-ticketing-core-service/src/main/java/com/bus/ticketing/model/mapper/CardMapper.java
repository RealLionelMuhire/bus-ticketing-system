package com.bus.ticketing.model.mapper;

import com.bus.ticketing.model.dto.Card;
import com.bus.ticketing.model.entity.CardEntity;
import org.springframework.beans.BeanUtils;

public class CardMapper extends BaseMapper<CardEntity, Card> {

    @Override
    public CardEntity convertToEntity(Card dto, Object... args) {
        CardEntity entity = new CardEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "user");
        }
        return entity;
    }

    @Override
    public Card convertToDto(CardEntity entity, Object... args) {
        Card dto = new Card();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "user");
            if (entity.getStatus() != null) {
                dto.setStatus(entity.getStatus().name());
            }
        }
        return dto;
    }
}