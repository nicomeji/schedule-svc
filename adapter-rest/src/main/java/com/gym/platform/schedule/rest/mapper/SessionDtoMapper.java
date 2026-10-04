package com.gym.platform.schedule.rest.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.domain.model.Session.ParticipantRegistration;
import com.gym.platform.schedule.domain.model.SessionFilters;
import com.gym.platform.schedule.rest.contract.SearchSessionDTO;
import com.gym.platform.schedule.rest.contract.SessionDTO;
import com.gym.platform.schedule.rest.contract.SessionRegistrationDTO;

@Mapper
public interface SessionDtoMapper {
    @Mapping(target = "timeRange.from", source = "startTime")
    @Mapping(target = "timeRange.to", source = "endTime")
    @Mapping(target = "zoneOffset", expression = "java(extractZoneOffset(dto.getStartTime()))")
    Session.BaseData toBaseData(SessionDTO dto);

    @Mapping(target = "startTime", expression = "java(toOffsetDateTime(model.getTimeRange().getFrom(), model.getZoneOffset()))")
    @Mapping(target = "endTime", expression = "java(toOffsetDateTime(model.getTimeRange().getTo(), model.getZoneOffset()))")
    SessionDTO toDto(Session model);

    SessionFilters toFiltersModel(SearchSessionDTO dto);

    SessionRegistrationDTO toRegistrationDto(ParticipantRegistration model);

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
