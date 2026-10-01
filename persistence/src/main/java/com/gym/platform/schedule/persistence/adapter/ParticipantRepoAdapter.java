package com.gym.platform.schedule.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gym.platform.schedule.domain.model.Participant;
import com.gym.platform.schedule.domain.repo.ParticipantRepo;
import com.gym.platform.schedule.persistence.mapper.ParticipantEntityMapper;
import com.gym.platform.schedule.persistence.repository.ParticipantRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParticipantRepoAdapter implements ParticipantRepo {
    private final ParticipantRepository repository;
    private final ParticipantEntityMapper mapper;

    @Override
    public Participant create(Participant participant) {
        return mapper.toDomain(repository.save(mapper.toEntity(participant)));
    }

    @Override
    public Optional<Participant> retrieve(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
