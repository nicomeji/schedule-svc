package com.gym.platform.schedule.domain.repo;

import java.util.Optional;

import com.gym.platform.schedule.domain.model.Participant;

public interface ParticipantRepo {
    Participant create(Participant.BaseData participantData);

    Optional<Participant> retrieve(Long id);
}
