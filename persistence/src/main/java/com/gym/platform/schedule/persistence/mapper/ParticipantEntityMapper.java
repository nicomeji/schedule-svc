package com.gym.platform.schedule.persistence.mapper;

import org.mapstruct.Mapper;

import com.gym.platform.schedule.domain.model.Participant;
import com.gym.platform.schedule.persistence.model.ParticipantEntity;

@Mapper
public interface ParticipantEntityMapper {
    Participant toDomain(ParticipantEntity entity);

    ParticipantEntity toEntity(Participant domain) throws NumberFormatException;
}
