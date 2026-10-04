package com.gym.platform.schedule.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gym.platform.schedule.domain.model.Participant;
import com.gym.platform.schedule.domain.repo.ParticipantRepo;
import com.gym.platform.schedule.persistence.mapper.ParticipantEntityMapper;
import com.gym.platform.schedule.persistence.model.ParticipantEntity;
import com.gym.platform.schedule.persistence.repository.ParticipantRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParticipantRepoAdapter implements ParticipantRepo {
    private final SessionRepoAdapter sessionRepoAdapter;
    private final ParticipantRepository repository;
    private final ParticipantEntityMapper mapper;

    @Override
    public Participant create(Participant.BaseData participantData) {
        return toDomain(repository.save(mapper.toNewEntity(participantData)));
    }

    @Override
    public Optional<Participant> retrieve(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    public List<Participant> retrieveSessionParticipants(Long sessionId) {
        return repository.findParticipantsBySessionId(sessionId).stream()
                .map(this::toDomain)
                .toList();
    }

    private Participant toDomain(ParticipantEntity entity) {
        return new Participant(
                entity.id(),
                mapper.toBaseData(entity),
                sessionRepoAdapter::retrieveParticipantSessions);
    }
}
