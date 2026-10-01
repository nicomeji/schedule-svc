package com.gym.platform.schedule.persistence.model;

import java.time.Instant;
import java.time.ZoneId;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("sessions")
public record SessionEntity(
        @Id Long id,
        Long coachId,
        String location,
        ZoneId timeZone,
        Instant startTime,
        Instant endTime,
        Integer capacity) {
}
