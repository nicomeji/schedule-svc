package com.gym.platform.schedule.rest.mapper;

import org.mapstruct.Mapper;

import com.gym.platform.schedule.domain.model.Session.ParticipantRegistration;
import com.gym.platform.schedule.rest.contract.SessionRegistrationDTO;

@Mapper
public interface SessionRegistrationDtoMapper {
    SessionRegistrationDTO toDto(ParticipantRegistration model);
}
