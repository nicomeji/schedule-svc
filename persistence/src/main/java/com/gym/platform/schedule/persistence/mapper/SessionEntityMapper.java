package com.gym.platform.schedule.persistence.mapper;

import org.mapstruct.Mapper;

import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.persistence.model.SessionEntity;

@Mapper
public interface SessionEntityMapper {
    SessionEntity toEntity(Session session) throws NumberFormatException;

    Session toDomain(SessionEntity session);
}
