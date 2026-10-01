package com.gym.platform.schedule.rest.mapper;

import org.mapstruct.Mapper;

import com.gym.platform.schedule.domain.model.Participant;
import com.gym.platform.schedule.rest.contract.ParticipantDTO;

@Mapper
public interface ParticipantDtoMapper {
    Participant toDomain(ParticipantDTO dto);

    ParticipantDTO toDto(Participant model);
}
