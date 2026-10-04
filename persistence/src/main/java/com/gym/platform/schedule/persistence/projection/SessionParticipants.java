package com.gym.platform.schedule.persistence.projection;

import java.time.Instant;

import lombok.Data;

@Data
public class SessionParticipants {
    private final Long participantId;
    private final Instant registeredAt;
}
