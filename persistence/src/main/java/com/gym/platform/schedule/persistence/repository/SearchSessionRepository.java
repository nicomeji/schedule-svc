package com.gym.platform.schedule.persistence.repository;

import com.gym.platform.schedule.domain.common.Page;
import com.gym.platform.schedule.domain.model.SessionFilters;
import com.gym.platform.schedule.persistence.model.SessionEntity;

public interface SearchSessionRepository {
    Page<SessionEntity> searchSessions(SessionFilters filters);
}
