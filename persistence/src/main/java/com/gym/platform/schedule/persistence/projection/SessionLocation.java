package com.gym.platform.schedule.persistence.projection;

import java.time.Instant;
import java.time.ZoneId;

public record SessionLocation(
        Long sesssionId,
        Long coachId,
        Instant startTime,
        Instant endTime,
        Integer capacity,
        Long locationId,
        String name,
        ZoneId timeZone) {
}
