package com.gym.platform.schedule.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class SessionTest {
    private Session createSession(Instant startTime, Instant endTime) {
        return new Session(
                1L,
                100L,
                "Gym Room A",
                ZoneOffset.UTC,
                startTime,
                endTime,
                10);
    }

    @Nested
    @DisplayName("isOverlapping")
    class IsOverlappingTests {

        private final Instant now = Instant.parse("2026-10-02T10:00:00Z");

        @Test
        @DisplayName("Should return true when sessions partially overlap")
        void shouldReturnTrueWhenSessionsPartiallyOverlap() {
            // Session A: 10:00 to 11:00
            Session sessionA = createSession(now, now.plusSeconds(3600));
            // Session B: 10:30 to 11:30
            Session sessionB = createSession(now.plusSeconds(1800), now.plusSeconds(5400));

            assertThat(sessionA.isOverlapping(sessionB)).isTrue();
            assertThat(sessionB.isOverlapping(sessionA)).isTrue();
        }

        @Test
        @DisplayName("Should return true when one session completely encloses another")
        void shouldReturnTrueWhenOneSessionEnclosesAnother() {
            // Session A: 10:00 to 12:00
            Session sessionA = createSession(now, now.plusSeconds(7200));
            // Session B: 10:30 to 11:30
            Session sessionB = createSession(now.plusSeconds(1800), now.plusSeconds(5400));

            assertThat(sessionA.isOverlapping(sessionB)).isTrue();
            assertThat(sessionB.isOverlapping(sessionA)).isTrue();
        }

        @Test
        @DisplayName("Should return true when sessions have equals time range")
        void shouldReturnTrueWhenSessionsOverlapEqualy() {
            // Session A: 10:00 to 11:00
            Session sessionA = createSession(now, now.plusSeconds(3600));
            // Session B: 10:00 to 11:00
            Session sessionB = createSession(now, now.plusSeconds(3600));

            assertThat(sessionA.isOverlapping(sessionB)).isTrue();
            assertThat(sessionB.isOverlapping(sessionA)).isTrue();
        }

        @Test
        @DisplayName("Should return false when sessions are adjacent (back-to-back)")
        void shouldReturnFalseWhenSessionsAreAdjacent() {
            // Session A: 10:00 to 11:00
            Session sessionA = createSession(now, now.plusSeconds(3600));
            // Session B: 11:00 to 12:00
            Session sessionB = createSession(now.plusSeconds(3600), now.plusSeconds(7200));

            assertThat(sessionA.isOverlapping(sessionB)).isFalse();
            assertThat(sessionB.isOverlapping(sessionA)).isFalse();
        }

        @Test
        @DisplayName("Should return false when sessions do not overlap at all")
        void shouldReturnFalseWhenSessionsDoNotOverlap() {
            // Session A: 10:00 to 11:00
            Session sessionA = createSession(now, now.plusSeconds(3600));
            // Session B: 12:00 to 13:00
            Session sessionB = createSession(now.plusSeconds(7200), now.plusSeconds(10800));

            assertThat(sessionA.isOverlapping(sessionB)).isFalse();
            assertThat(sessionB.isOverlapping(sessionA)).isFalse();
        }
    }
}
