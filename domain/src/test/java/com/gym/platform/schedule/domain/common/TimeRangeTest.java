package com.gym.platform.schedule.domain.common;

import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TimeRangeTest {

    private final Instant t1 = Instant.parse("2026-01-01T10:00:00Z");
    private final Instant t2 = Instant.parse("2026-01-01T11:00:00Z");
    private final Instant t3 = Instant.parse("2026-01-01T12:00:00Z");
    private final Instant t4 = Instant.parse("2026-01-01T13:00:00Z");

    @Test
    @DisplayName("Should return true when sessions partially overlap")
    void shouldReturnTrueWhenSessionsPartiallyOverlap() {
        TimeRange range1 = new TimeRange(t1, t3); // 10:00 - 12:00
        TimeRange range2 = new TimeRange(t2, t4); // 11:00 - 13:00

        assertThat(range1.isOverlapping(range2)).isTrue();
        assertThat(range2.isOverlapping(range1)).isTrue();
    }

    @Test
    @DisplayName("Should return true when one session completely encloses another")
    void shouldReturnTrueWhenOneSessionCompletelyEnclosesAnother() {
        TimeRange outer = new TimeRange(t1, t4); // 10:00 - 13:00
        TimeRange inner = new TimeRange(t2, t3); // 11:00 - 12:00

        assertThat(outer.isOverlapping(inner)).isTrue();
        assertThat(inner.isOverlapping(outer)).isTrue();
    }

    @Test
    @DisplayName("Should return true when sessions have equals time range")
    void shouldReturnTrueWhenSessionsHaveEqualsTimeRange() {
        TimeRange range1 = new TimeRange(t1, t2); // 10:00 - 11:00
        TimeRange range2 = new TimeRange(t1, t2); // 10:00 - 11:00

        assertThat(range1.isOverlapping(range2)).isTrue();
    }

    @Test
    @DisplayName("Should return false when sessions are adjacent (back-to-back)")
    void shouldReturnFalseWhenSessionsAreAdjacent() {
        TimeRange range1 = new TimeRange(t1, t2); // 10:00 - 11:00
        TimeRange range2 = new TimeRange(t2, t3); // 11:00 - 12:00

        assertThat(range1.isOverlapping(range2)).isFalse();
        assertThat(range2.isOverlapping(range1)).isFalse();
    }

    @Test
    @DisplayName("Should return false when sessions do not overlap at all")
    void shouldReturnFalseWhenSessionsDoNotOverlapAtAll() {
        TimeRange range1 = new TimeRange(t1, t2); // 10:00 - 11:00
        TimeRange range2 = new TimeRange(t3, t4); // 12:00 - 13:00

        assertThat(range1.isOverlapping(range2)).isFalse();
        assertThat(range2.isOverlapping(range1)).isFalse();
    }
}
