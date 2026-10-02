package com.gym.platform.schedule.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.platform.schedule.application.exceptions.BusinessException;
import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.domain.repo.CoachRepo;
import com.gym.platform.schedule.domain.repo.SessionRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateSession {
    private final CoachRepo coachRepo;
    private final SessionRepo sessionRepo;

    @Transactional
    public Session createSession(Session session) {
        boolean isOverlapping = coachRepo.retrieve(session.coachId())
                .map(sessionRepo::retrieveCoachSessions)
                .map(coachSessions -> coachSessions.stream().anyMatch(session::isOverlapping))
                .orElse(false);
        if (isOverlapping) {
            throw new BusinessException.SessionOverlapException();
        }
        return sessionRepo.create(session.withoutId());
    }
}
