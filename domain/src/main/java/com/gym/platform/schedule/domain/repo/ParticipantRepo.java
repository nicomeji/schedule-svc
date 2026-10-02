package com.gym.platform.schedule.domain.repo;

import java.util.List;
import java.util.Optional;

import com.gym.platform.schedule.domain.model.Participant;

public interface ParticipantRepo {
    Participant create(Participant participant);

    Optional<Participant> retrieve(Long id);

    List<Participant> retrieveSessionParticipants(Long sessionId);
}
