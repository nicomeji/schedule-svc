package com.gym.platform.schedule.application.usecase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.platform.schedule.application.exceptions.BusinessException;
import com.gym.platform.schedule.domain.model.Participant;
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
    public boolean addParticipant(Long sessionId, Long participantId) {
        Session session = sessionRepo.retrieve(sessionId).orElseThrow(BusinessException.NotFouncException::new);
        var participantIds = participantRepo.retrieveSessionParticipants(session.id()).stream()
                .map(Participant::id)
                .collect(Collectors.toList());
        if (session.capacity() <= participantIds.size()) {
            throw new BusinessException.SessoinCapacityExceededException();
        }
        if (participantIds.contains(participantId)) {
            throw new BusinessException.DuplicateSessionRegistrationException();
        }
        return participantRepo.retrieve(participantId)
                .map(participant -> sessionRepo.registerParticipant(sessionId, participant))
                .orElse(false);
    }
}
