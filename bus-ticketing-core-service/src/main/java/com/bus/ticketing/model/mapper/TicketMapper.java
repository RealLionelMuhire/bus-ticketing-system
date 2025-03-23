package com.bus.ticketing.model.mapper;

import com.bus.ticketing.model.dto.Ticket;
import com.bus.ticketing.model.entity.TicketEntity;
import org.springframework.beans.BeanUtils;

public class TicketMapper extends BaseMapper<TicketEntity, Ticket> {
    private TripMapper tripMapper = new TripMapper();
    private UserMapper userMapper = new UserMapper();

    @Override
    public TicketEntity convertToEntity(Ticket dto, Object... args) {
        // Use constructor initialization since we don't have access to the builder here
        TicketEntity entity = new TicketEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "trip", "passenger", "agent", "status", "paymentMethod");

            // Handle enum conversions
            if (dto.getStatus() != null) {
                entity.setStatus(com.bus.ticketing.model.TicketStatus.valueOf(dto.getStatus()));
            }

            // Add check for paymentMethod field
            if (dto.getPaymentMethod() != null) {
                try {
                    entity.setPaymentMethod(com.bus.ticketing.model.PaymentMethod.valueOf(dto.getPaymentMethod()));
                } catch (IllegalArgumentException e) {
                    // Handle case where the payment method string doesn't match any enum value
                    // Log the error or set a default value
                }
            }

            // Handle complex objects
            if (dto.getTrip() != null) {
                entity.setTrip(tripMapper.convertToEntity(dto.getTrip()));
            }

            if (dto.getPassenger() != null) {
                entity.setPassenger(userMapper.convertToEntity(dto.getPassenger()));
            }

            if (dto.getAgent() != null) {
                entity.setAgent(userMapper.convertToEntity(dto.getAgent()));
            }
        }
        return entity;
    }

    @Override
    public Ticket convertToDto(TicketEntity entity, Object... args) {
        Ticket dto = new Ticket();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "trip", "passenger", "agent", "status", "paymentMethod");

            // Handle enum conversions
            if (entity.getStatus() != null) {
                dto.setStatus(entity.getStatus().name());
            }

            // Add check for paymentMethod field
            if (entity.getPaymentMethod() != null) {
                dto.setPaymentMethod(entity.getPaymentMethod().name());
            }

            // Handle complex objects
            if (entity.getTrip() != null) {
                dto.setTrip(tripMapper.convertToDto(entity.getTrip()));
            }

            if (entity.getPassenger() != null) {
                dto.setPassenger(userMapper.convertToDto(entity.getPassenger()));
            }

            if (entity.getAgent() != null) {
                dto.setAgent(userMapper.convertToDto(entity.getAgent()));
            }
        }
        return dto;
    }
}