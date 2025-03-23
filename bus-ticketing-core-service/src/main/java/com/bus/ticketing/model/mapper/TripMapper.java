package com.bus.ticketing.model.mapper;

import com.bus.ticketing.model.dto.Trip;
import com.bus.ticketing.model.entity.TripEntity;
import org.springframework.beans.BeanUtils;

public class TripMapper extends BaseMapper<TripEntity, Trip> {
    private RouteMapper routeMapper = new RouteMapper();
    private BusMapper busMapper = new BusMapper();

    @Override
    public TripEntity convertToEntity(Trip dto, Object... args) {
        TripEntity entity = new TripEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "route", "bus", "tickets", "status");

            // Handle enum conversion
            if (dto.getStatus() != null) {
                entity.setStatus(com.bus.ticketing.model.TripStatus.valueOf(dto.getStatus()));
            }

            // Handle complex objects if they exist in the DTO
            if (dto.getRoute() != null) {
                entity.setRoute(routeMapper.convertToEntity(dto.getRoute()));
            }

            if (dto.getBus() != null) {
                entity.setBus(busMapper.convertToEntity(dto.getBus()));
            }
        }
        return entity;
    }

    @Override
    public Trip convertToDto(TripEntity entity, Object... args) {
        Trip dto = new Trip();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "route", "bus", "tickets", "status");

            // Handle enum conversion
            if (entity.getStatus() != null) {
                dto.setStatus(entity.getStatus().name());
            }

            // Handle complex objects
            if (entity.getRoute() != null) {
                dto.setRoute(routeMapper.convertToDto(entity.getRoute()));
            }

            if (entity.getBus() != null) {
                dto.setBus(busMapper.convertToDto(entity.getBus()));
            }
        }
        return dto;
    }
}