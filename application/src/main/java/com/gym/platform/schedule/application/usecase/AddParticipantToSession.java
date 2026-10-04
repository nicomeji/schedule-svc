package com.gym.platform.schedule.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.platform.schedule.domain.exceptions.BusinessException;
import com.gym.platform.schedule.domain.model.Session;
import com.gym.platform.schedule.domain.repo.ParticipantRepo;
import com.gym.platform.schedule.domain.repo.SessionRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddParticipantToSession {
    private final SessionRepo sessionRepo;
    private final ParticipantRepo participantRepo;

    @Transactional
    public void addParticipant(Long sessionId, Long participantId) {
        var session = sessionRepo.retrieve(sessionId)
                .orElseThrow(BusinessException.NotFouncException::new);

        var participant = participantRepo.retrieve(participantId)
                .orElseThrow(BusinessException.NotFouncException::new);

        var participantIds = session.retrieveRegistrations().stream()
                .map(Session.ParticipantRegistration::getParticipantId)
                .toList();

        if (session.getCapacity() <= participantIds.size()) {
            throw new BusinessException.SessoinCapacityExceededException();
        }
        if (participantIds.contains(participantId)) {
            throw new BusinessException.DuplicateSessionRegistrationException();
        }

        var isOverlapping = participant.retrieveSessions().stream()
                .map(Session::getTimeRange)
                .anyMatch(session.getTimeRange()::isOverlapping);

        if (isOverlapping) {
            throw new BusinessException.SessionOverlapException();
        }

        sessionRepo.addParticipant(session, participant);
    }
}
