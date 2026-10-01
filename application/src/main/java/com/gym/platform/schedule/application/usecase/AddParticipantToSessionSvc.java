package com.gym.platform.schedule.application.usecase;

import org.springframework.stereotype.Service;

import com.gym.platform.schedule.application.service.ParticipantCrudSvc;
import com.gym.platform.schedule.application.service.SessionCrudSvc;
import com.gym.platform.schedule.domain.model.Session;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddParticipantToSessionSvc {
    private final SessionCrudSvc sessionSvc;
    private final ParticipantCrudSvc participantSvc;

    public Session addParticipant(String sessionId, String participantId) {
        return null;
    }
}
