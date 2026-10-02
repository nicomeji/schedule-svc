package com.gym.platform.schedule.domain.model;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;

public record Session(
                Long id,
                Long coachId,
                String location,
                ZoneOffset zoneOffset,
                Instant startTime,
                Instant endTime,
                Integer capacity) {
        public Session {
                Objects.requireNonNull(coachId, "coachId cannot be null");
                Objects.requireNonNull(location, "location cannot be null");
                Objects.requireNonNull(zoneOffset, "zoneOffset cannot be null");
                Objects.requireNonNull(startTime, "startTime cannot be null");
                Objects.requireNonNull(endTime, "endTime cannot be null");
                Objects.requireNonNull(capacity, "capacity cannot be null");

                if (!startTime.isBefore(endTime)) {
                        throw new IllegalArgumentException("startTime must be before endTime");
                }
        }

        public Session withoutId() {
                return new Session(null, this.coachId, this.location, this.zoneOffset, this.startTime, this.endTime,
                                this.capacity);
        }

        public boolean isOverlapping(Session other) {
                return this.startTime().isBefore(other.endTime()) && other.startTime().isBefore(this.endTime());
        }
}
