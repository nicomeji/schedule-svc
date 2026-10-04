package com.gym.platform.schedule.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gym.platform.schedule.domain.common.Page;
import com.gym.platform.schedule.domain.exceptions.BusinessException;
import com.gym.platform.schedule.domain.model.Coach;
import com.gym.platform.schedule.domain.model.Participant;
import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.domain.model.SessionFilters;
import com.gym.platform.schedule.domain.repo.SessionRepo;
import com.gym.platform.schedule.persistence.mapper.SessionEntityMapper;
import com.gym.platform.schedule.persistence.model.SessionEntity;
import com.gym.platform.schedule.persistence.repository.SessionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SessionRepoAdapter implements SessionRepo {
    private final SessionRepository repository;
    private final SessionEntityMapper mapper;

    @Override
    public Session create(Session.BaseData sessionData) {
        return toDomain(repository.save(mapper.toNewEntity(sessionData)));
    }

    @Override
    public Optional<Session> retrieve(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Page<Session> search(SessionFilters filters) {
        return repository.searchSessions(filters).map(this::toDomain);
    }

    @Override
    public void addParticipant(Session session, Participant participant) {
        if (!repository.addParticipant(session.getId(), participant.getId())) {
            throw new BusinessException.DuplicateSessionRegistrationException();
        }
    }

    @Override
    public void removeParticipant(Session session, Participant participant) {
        repository.removeParticipant(session.getId(), participant.getId());
    }

    @Override
    public void delete(Session session) {
        repository.delete(mapper.toExistingEntity(session));
    }

    List<Session> retrieveCoachSessions(Coach coach) {
        return repository.findSessionsByCoachId(coach.getId()).stream()
                .map(this::toDomain)
                .toList();
    }

    List<Session> retrieveParticipantSessions(Participant participant) {
        return repository.findSessionsByParticipantId(participant.getId()).stream()
                .map(this::toDomain)
                .toList();
    }

    List<Session.ParticipantRegistration> retrieveSessionRegistrations(Session session) {
        return repository.findSessionParticipantsBySessionId(session.getId()).stream()
                .map(mapper::mapRegistration)
                .toList();
    }

    private Session toDomain(SessionEntity entity) {
        return new Session(
                entity.id(),
                mapper.toBaseData(entity),
                this::retrieveSessionRegistrations);
    }
}
