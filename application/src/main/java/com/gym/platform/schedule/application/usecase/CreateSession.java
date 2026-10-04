package com.gym.platform.schedule.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.platform.schedule.domain.exceptions.BusinessException;
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
    public Session createSession(Session.BaseData sessionData) {
        var coach = coachRepo.retrieve(sessionData.getCoachId())
                .orElseThrow(BusinessException.NotFouncException::new);

        var isOverlapping = coach.retrieveSessions().stream()
                .map(Session::getTimeRange)
                .anyMatch(sessionData.getTimeRange()::isOverlapping);

        if (isOverlapping) {
            throw new BusinessException.SessionOverlapException();
        }

        return sessionRepo.create(sessionData);
    }
}
