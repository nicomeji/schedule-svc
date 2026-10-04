package com.gym.platform.schedule.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gym.platform.schedule.domain.model.Coach;
import com.gym.platform.schedule.domain.repo.CoachRepo;
import com.gym.platform.schedule.persistence.mapper.CoachEntityMapper;
import com.gym.platform.schedule.persistence.model.CoachEntity;
import com.gym.platform.schedule.persistence.repository.CoachRepository;

import lombok.Data;

@Data
@Service
public class CoachRepoAdapter implements CoachRepo {
    private final SessionRepoAdapter sessionRepoAdapter;
    private final CoachRepository repository;
    private final CoachEntityMapper mapper;

    @Override
    public Coach create(Coach.BaseData coachData) {
        return toDomain(repository.save(mapper.toNewEntity(coachData)));
    }

    @Override
    public Optional<Coach> retrieve(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    private Coach toDomain(CoachEntity entity) {
        return new Coach(
                entity.id(),
                mapper.toBaseData(entity),
                sessionRepoAdapter::retrieveCoachSessions);
    }
}
