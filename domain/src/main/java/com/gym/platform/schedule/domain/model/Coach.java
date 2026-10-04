package com.gym.platform.schedule.domain.model;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;

@AllArgsConstructor
public class Coach {
    @Getter
    @NonNull
    private final Long id;

    @Delegate
    @NonNull
    private final BaseData data;

    @NonNull
    private final Function<Coach, List<Session>> retrieveSessions;

    public List<Session> retrieveSessions() {
        return retrieveSessions.apply(this);
    }

    @Data
    public static class BaseData {
        @NonNull
        private final String email;

        @NonNull
        private final String firstName;

        @NonNull
        private final String lastName;
    }
}
