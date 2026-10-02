package com.gym.platform.schedule.rest.contract;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class SessionRegistrationDTO {
    @Data
    public static class CreateRegistration {
        @NotNull
        private Long participantId;
    }

    @Data
    public static class SessionRegistration {
        private OffsetDateTime registeredAt;
        private Long participantId;
    }
}
