package com.gym.platform.schedule.domain.model;

import java.time.Instant;
import java.time.ZoneOffset;

public record Session(
                Long id,
                Long coachId,
                String location,
                ZoneOffset zoneOffset,
                Instant startTime,
                Instant endTime,
                Integer capacity) {
        public Session withoutId() {
                return new Session(null, this.coachId, this.location, this.zoneOffset, this.startTime, this.endTime,
                                this.capacity);
        }
}
