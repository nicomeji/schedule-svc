package com.gym.platform.schedule.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gym.platform.schedule.domain.common.Page;
import com.gym.platform.schedule.domain.model.Coach;
import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.domain.model.SessionFilters;
import com.gym.platform.schedule.domain.repo.SessionRepo;
import com.gym.platform.schedule.persistence.mapper.SessionEntityMapper;
import com.gym.platform.schedule.persistence.repository.SessionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SessionRepoAdapter implements SessionRepo {
    private final SessionRepository repository;
    private final SessionEntityMapper mapper;

    @Override
    public Session create(Session session) {
        return mapper.toDomain(repository.save(mapper.toEntity(session)));
    }

    @Override
    public Optional<Session> retrieve(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Session> search(SessionFilters filters) {
        return repository.searchSessions(filters).map(mapper::toDomain);
    }

    @Override
    public List<Session> retrieveCoachSessions(Coach coach) {
        return repository.findSessionsByCoachId(coach.id()).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
