package com.gym.platform.schedule.domain.model;

import java.time.Instant;
import java.time.ZoneId;

public record Session(
                Long id,
                Long coachId,
                String location,
                ZoneId timeZone,
                Instant startTime,
                Instant endTime,
                Integer capacity) {
        public Session withoutId() {
                return new Session(null, this.coachId, this.location, this.timeZone, this.startTime, this.endTime,
                                this.capacity);
        }
}
