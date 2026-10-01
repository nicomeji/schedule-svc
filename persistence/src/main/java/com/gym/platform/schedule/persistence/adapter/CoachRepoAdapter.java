package com.gym.platform.schedule.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gym.platform.schedule.domain.model.Coach;
import com.gym.platform.schedule.domain.repo.CoachRepo;
import com.gym.platform.schedule.persistence.mapper.CoachEntityMapper;
import com.gym.platform.schedule.persistence.repository.CoachRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CoachRepoAdapter implements CoachRepo {
    private final CoachRepository repository;
    private final CoachEntityMapper mapper;

    @Override
    public Coach create(Coach coach) {
        return mapper.toDomain(repository.save(mapper.toEntity(coach)));
    }

    @Override
    public Optional<Coach> retrieve(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
