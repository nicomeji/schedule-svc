package com.gym.platform.schedule.domain.repo;

import java.util.Optional;

import com.gym.platform.schedule.domain.common.Page;
import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.domain.model.SessionFilters;

public interface SessionRepo {
    Session create(Session session);

    Optional<Session> retrieve(Long id);

    Page<Session> search(SessionFilters filters);
}
