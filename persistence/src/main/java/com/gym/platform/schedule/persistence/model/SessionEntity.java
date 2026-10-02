package com.gym.platform.schedule.persistence.model;

import java.time.Instant;
import java.time.ZoneOffset;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("sessions")
public record SessionEntity(
        @Id Long id,
        Long coachId,
        String location,
        ZoneOffset zoneOffset,
        Instant startTime,
        Instant endTime,
        Integer capacity) {
}
