package com.gym.platform.schedule.rest.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.domain.model.SessionFilters;
import com.gym.platform.schedule.rest.contract.SessionDTO;

@Mapper
public interface SessionDtoMapper {
    // =========================================================================
    // SessionDTO.CreateSession -> SessionModel
    // =========================================================================
    @Mapping(target = "id", ignore = true) // id no existe en CreateSession
    @Mapping(target = "coachId", source = "sessionData.coachId")
    @Mapping(target = "location", source = "sessionData.location")
    @Mapping(target = "startTime", source = "sessionData.startTime")
    @Mapping(target = "endTime", source = "sessionData.endTime")
    @Mapping(target = "capacity", source = "sessionData.capacity")
    @Mapping(target = "zoneOffset", expression = "java(extractZoneOffset(dto.getSessionData().getStartTime()))")
    Session toModel(SessionDTO.CreateSession dto);

    // =========================================================================
    // SessionModel -> SessionDTO.Session
    // =========================================================================
    @Mapping(target = "id", source = "id")
    @Mapping(target = "sessionData.coachId", source = "coachId")
    @Mapping(target = "sessionData.location", source = "location")
    @Mapping(target = "sessionData.capacity", source = "capacity")
    @Mapping(target = "sessionData.startTime", expression = "java(toOffsetDateTime(session.startTime(), session.zoneOffset()))")
    @Mapping(target = "sessionData.endTime", expression = "java(toOffsetDateTime(session.endTime(), session.zoneOffset()))")
    SessionDTO.Session toSessionDTO(Session model);

    SessionFilters toModel(SessionDTO.SearchSessionDTO dto);

    // =========================================================================
    // Auxiliar mappings
    // =========================================================================
    default Instant toInstant(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toInstant() : null;
    }

    default ZoneOffset extractZoneOffset(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.getOffset() : ZoneOffset.UTC;
    }

    default OffsetDateTime toOffsetDateTime(Instant instant, ZoneOffset offset) {
        if (instant == null) {
            return null;
        }
        if (offset == null) {
            return instant.atOffset(ZoneOffset.UTC);
        }
        return instant.atOffset(offset);
    }
}
