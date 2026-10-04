package com.gym.platform.schedule.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gym.platform.schedule.domain.model.Participant;
import com.gym.platform.schedule.domain.repo.ParticipantRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParticipantCrudSvc {
    private final ParticipantRepo repo;

    public Participant create(Participant.BaseData participantData) {
        return repo.create(participantData);
    }

    public Optional<Participant> retrieve(Long id) {
        return repo.retrieve(id);
    }
}
