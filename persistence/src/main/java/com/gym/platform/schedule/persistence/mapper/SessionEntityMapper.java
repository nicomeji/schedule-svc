package com.gym.platform.schedule.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.persistence.model.SessionEntity;
import com.gym.platform.schedule.persistence.projection.SessionParticipants;

@Mapper
public interface SessionEntityMapper {
    @Mapping(target = "timeRange.from", source = "startTime")
    @Mapping(target = "timeRange.to", source = "endTime")
    Session.BaseData toBaseData(SessionEntity entity);

    // Used to create a new record
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "startTime", source = "timeRange.from")
    @Mapping(target = "endTime", source = "timeRange.to")
    SessionEntity toNewEntity(Session.BaseData data);

    // Used to update an existing record
    @Mapping(target = "startTime", source = "timeRange.from")
    @Mapping(target = "endTime", source = "timeRange.to")
    SessionEntity toExistingEntity(Session data);

    Session.ParticipantRegistration mapRegistration(SessionParticipants sp);
}
