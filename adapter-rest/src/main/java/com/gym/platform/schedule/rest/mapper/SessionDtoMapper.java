package com.gym.platform.schedule.rest.mapper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    @Mapping(target = "timeZone", expression = "java(extractZoneId(dto.getSessionData().getStartTime()))")
    Session toModel(SessionDTO.CreateSession dto);

    // =========================================================================
    // SessionModel -> SessionDTO.Session
    // =========================================================================
    @Mapping(target = "id", source = "id")
    @Mapping(target = "sessionData.coachId", source = "coachId")
    @Mapping(target = "sessionData.location", source = "location")
    @Mapping(target = "sessionData.capacity", source = "capacity")
    @Mapping(target = "sessionData.startTime", expression = "java(toZonedDateTime(session.startTime(), session.timeZone()))")
    @Mapping(target = "sessionData.endTime", expression = "java(toZonedDateTime(session.endTime(), session.timeZone()))")
    SessionDTO.Session toSessionDTO(Session model);

    SessionFilters toModel(SessionDTO.SearchSessionDTO dto);

    // =========================================================================
    // Auxiliar mappings
    // =========================================================================
    default Instant toInstant(ZonedDateTime zonedDateTime) {
        return zonedDateTime != null ? zonedDateTime.toInstant() : null;
    }

    default ZoneId extractZoneId(ZonedDateTime zonedDateTime) {
        return zonedDateTime != null ? zonedDateTime.getZone() : ZoneId.systemDefault();
    }

    default ZonedDateTime toZonedDateTime(Instant instant, ZoneId zoneId) {
        if (instant == null) {
            return null;
        }
        ZoneId zone = (zoneId != null) ? zoneId : ZoneId.systemDefault();
        return instant.atZone(zone);
    }
}
