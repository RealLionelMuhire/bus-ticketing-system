package com.bus.ticketing.model.mapper;

import com.bus.ticketing.model.dto.Company;
import com.bus.ticketing.model.entity.CompanyEntity;
import org.springframework.beans.BeanUtils;

public class CompanyMapper extends BaseMapper<CompanyEntity, Company> {
    private BusMapper busMapper = new BusMapper();

    @Override
    public CompanyEntity convertToEntity(Company dto, Object... args) {
        CompanyEntity entity = new CompanyEntity();
        if (dto != null) {
            BeanUtils.copyProperties(dto, entity, "buses");

            if (dto.getBuses() != null) {
                entity.setBuses(busMapper.convertToEntityList(dto.getBuses()));
            }
        }
        return entity;
    }

    @Override
    public Company convertToDto(CompanyEntity entity, Object... args) {
        Company dto = new Company();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto, "buses");

            if (entity.getBuses() != null) {
                dto.setBuses(busMapper.convertToDtoList(entity.getBuses()));
            }
        }
        return dto;
    }
}