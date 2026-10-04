package com.gym.platform.schedule.rest.mapper;

import org.mapstruct.Mapper;

import com.gym.platform.schedule.domain.model.Coach;
import com.gym.platform.schedule.rest.contract.CoachDTO;

@Mapper
public interface CoachDtoMapper {
    Coach.BaseData toBaseData(CoachDTO dto);

    CoachDTO toDto(Coach model);
}
