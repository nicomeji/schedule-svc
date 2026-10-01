package com.gym.platform.schedule.persistence.projection;

import java.time.Instant;

public record ParticipantRegistration(
        Long participantId,
        String name,
        String email,
        Instant registeredAt) {
}
