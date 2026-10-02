package com.gym.platform.schedule.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gym.platform.schedule.domain.common.Page;
import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.domain.model.SessionFilters;
import com.gym.platform.schedule.domain.repo.SessionRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SessionCrudSvc {
    private final SessionRepo repo;

    public Optional<Session> retrieve(Long id) {
        return repo.retrieve(id);
    }

    public Page<Session> search(SessionFilters filters) {
        return repo.search(filters);
    }
}
