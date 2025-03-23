package com.bus.ticketing.model.mapper;

import com.bus.ticketing.model.dto.User;
import com.bus.ticketing.model.entity.UserEntity;
import org.springframework.beans.BeanUtils;

public class UserMapper extends BaseMapper<UserEntity, User> {
    private CardMapper cardMapper = new CardMapper();

    @Override
    public UserEntity convertToEntity(User dto, Object... args) {
        UserEntity entity = new UserEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "cards");
            if (dto.getCards() != null) {
                entity.setCards(cardMapper.convertToEntityList(dto.getCards()));
            }
        }
        return entity;
    }

    @Override
    public User convertToDto(UserEntity entity, Object... args) {
        User dto = new User();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "cards");
            if (entity.getCards() != null) {
                dto.setCards(cardMapper.convertToDtoList(entity.getCards()));
            }
        }
        return dto;
    }
}