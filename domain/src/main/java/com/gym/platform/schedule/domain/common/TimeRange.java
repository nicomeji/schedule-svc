package com.gym.platform.schedule.domain.common;

import java.time.Instant;

public record TimeRange(
        Instant from,
        Instant to) {
}
