package com.gym.platform.schedule.domain.model;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.function.Function;

import com.gym.platform.schedule.domain.common.TimeRange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;

@AllArgsConstructor
public class Session {
    @Getter
    @NonNull
    private final Long id;

    @Delegate
    @NonNull
    private final BaseData data;

    @NonNull
    private final Function<Session, List<ParticipantRegistration>> retrieveRegistrations;

    public List<ParticipantRegistration> retrieveRegistrations() {
        return retrieveRegistrations.apply(this);
    }

    @Data
    public static class BaseData {
        @NonNull
        private final Long coachId;

        @NonNull
        private final String location;

        @NonNull
        private final ZoneOffset zoneOffset;

        @NonNull
        private final TimeRange.ClosedTimeRange timeRange;

        @NonNull
        private final Integer capacity;
    }

    @Data
    public static class ParticipantRegistration {
        @NonNull
        private final Long participantId;

        @NonNull
        private final Instant registeredAt;
    }
}
