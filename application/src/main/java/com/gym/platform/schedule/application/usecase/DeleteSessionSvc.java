package com.gym.platform.schedule.application.usecase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.platform.schedule.domain.exceptions.BusinessException;
import com.gym.platform.schedule.domain.model.Session.ParticipantRegistration;
import com.gym.platform.schedule.domain.repo.ParticipantRepo;
import com.gym.platform.schedule.domain.repo.SessionRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteSessionSvc {
    private final SessionRepo sessionRepo;
    private final ParticipantRepo participantRepo;

    @Transactional
    public void deleteSession(Long sessionId, List<Long> participantIds) {
        var session = sessionRepo.retrieve(sessionId).orElseThrow(BusinessException.NotFouncException::new);

        var currentParticipantIds = session.retrieveRegistrations().stream()
                .map(ParticipantRegistration::getParticipantId)
                .collect(Collectors.toSet());

        if (!toSet(participantIds).equals(currentParticipantIds)) {
            throw new BusinessException.NotEmpstySessionException();
        }

        currentParticipantIds.forEach(participantId -> {
            var participant = participantRepo.retrieve(participantId)
                    .orElseThrow(BusinessException.NotFouncException::new);
            sessionRepo.removeParticipant(session, participant);
        });

        sessionRepo.delete(session);
    }

    private Set<Long> toSet(List<Long> ids) {
        return new HashSet<>(ids);
    }
}
