package com.gym.platform.schedule.persistence.mapper;

import org.mapstruct.Mapper;

import com.gym.platform.schedule.domain.model.Coach;
import com.gym.platform.schedule.persistence.model.CoachEntity;

@Mapper
public interface CoachEntityMapper {
    Coach toDomain(CoachEntity entity);

    CoachEntity toEntity(Coach domain) throws NumberFormatException;
}
