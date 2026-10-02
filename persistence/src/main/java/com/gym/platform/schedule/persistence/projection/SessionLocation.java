package com.gym.platform.schedule.persistence.projection;

import java.time.Instant;
import java.time.ZoneOffset;

public record SessionLocation(
        Long sesssionId,
        Long coachId,
        Instant startTime,
        Instant endTime,
        Integer capacity,
        Long locationId,
        String name,
        ZoneOffset zoneOffset) {
}
