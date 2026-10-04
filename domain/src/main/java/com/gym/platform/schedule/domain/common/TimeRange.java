package com.gym.platform.schedule.domain.common;

import java.time.Instant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
public class TimeRange {
    private final Instant from;
    private final Instant to;

    public TimeRange(Instant from, Instant to) {
        if (from != null && to != null && !from.isBefore(to)) {
            throw new IllegalArgumentException("Illegal time range");
        }
        this.from = from;
        this.to = to;
    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static final class ClosedTimeRange extends TimeRange {
        public ClosedTimeRange(@NonNull Instant from, @NonNull Instant to) {
            super(from, to);
        }
    }

    public boolean isOverlapping(TimeRange other) {
        if (other == null) {
            return false;
        }

        boolean thisStartBeforeOtherEnd = isBeforeWithInfinite(this.getFrom(), other.getTo());
        boolean otherStartBeforeThisEnd = isBeforeWithInfinite(other.getFrom(), this.getTo());

        return thisStartBeforeOtherEnd && otherStartBeforeThisEnd;
    }

    private boolean isBeforeWithInfinite(Instant start, Instant end) {
        // start == null means "from the beginning of time"
        // to == null means "until the end of time"
        if (start == null || end == null) {
            return true;
        }
        return start.isBefore(end);
    }
}
