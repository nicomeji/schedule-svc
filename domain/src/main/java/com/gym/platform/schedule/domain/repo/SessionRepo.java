package com.gym.platform.schedule.domain.repo;

import java.util.Optional;

import com.gym.platform.schedule.domain.common.Page;
import com.gym.platform.schedule.domain.model.Participant;
import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.domain.model.SessionFilters;

public interface SessionRepo {
    Session create(Session.BaseData sessionData);

    Optional<Session> retrieve(Long id);

    Page<Session> search(SessionFilters filters);

    Session registerParticipant(Session session, Participant participant);
}
