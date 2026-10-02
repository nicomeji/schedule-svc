package com.gym.platform.schedule.persistence.projection;

import java.time.Instant;

public record ParticipantRegistration(
                Long participantId,
                Instant registeredAt) {
}
