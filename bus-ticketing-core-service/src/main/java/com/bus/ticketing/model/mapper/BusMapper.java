package com.bus.ticketing.model.mapper;

import com.bus.ticketing.model.dto.Bus;
import com.bus.ticketing.model.entity.BusEntity;
import org.springframework.beans.BeanUtils;

public class BusMapper extends BaseMapper<BusEntity, Bus> {

    @Override
    public BusEntity convertToEntity(Bus dto, Object... args) {
        BusEntity entity = new BusEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "company", "driver", "trips");
            // Company and driver would be set separately if needed
        }
        return entity;
    }

    @Override
    public Bus convertToDto(BusEntity entity, Object... args) {
        Bus dto = new Bus();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "company", "driver", "trips");
            if (entity.getStatus() != null) {
                dto.setStatus(entity.getStatus().name());
            }
        }
        return dto;
    }
}