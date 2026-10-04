package com.gym.platform.schedule.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.platform.schedule.domain.exceptions.BusinessException;
import com.gym.platform.schedule.domain.repo.ParticipantRepo;
import com.gym.platform.schedule.domain.repo.SessionRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RemoveParticipantFromSession {
    private final SessionRepo sessionRepo;
    private final ParticipantRepo participantRepo;

    @Transactional
    public void removeParticipant(Long sessionId, Long participantId) {
        var session = sessionRepo.retrieve(sessionId)
                .orElseThrow(BusinessException.NotFouncException::new);

        var participant = participantRepo.retrieve(participantId)
                .orElseThrow(BusinessException.NotFouncException::new);

        sessionRepo.removeParticipant(session, participant);
    }
}
