package com.bus.ticketing.model.mapper;

import com.bus.ticketing.model.dto.Route;
import com.bus.ticketing.model.entity.RouteEntity;
import org.springframework.beans.BeanUtils;

public class RouteMapper extends BaseMapper<RouteEntity, Route> {

    @Override
    public RouteEntity convertToEntity(Route dto, Object... args) {
        RouteEntity entity = new RouteEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "trips");
            // Stops are copied directly since they're just strings
        }
        return entity;
    }

    @Override
    public Route convertToDto(RouteEntity entity, Object... args) {
        Route dto = new Route();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "trips");
            // Stops are copied directly since they're just strings
        }
        return dto;
    }
}