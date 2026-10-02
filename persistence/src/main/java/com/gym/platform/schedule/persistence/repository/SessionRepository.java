package com.gym.platform.schedule.persistence.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.gym.platform.schedule.persistence.model.SessionEntity;
import com.gym.platform.schedule.persistence.projection.ParticipantRegistration;

public interface SessionRepository extends SearchSessionRepository, CrudRepository<SessionEntity, Long> {
    @Query("""
                SELECT s.*
                FROM sessions s
                JOIN coaches c ON s.coach_id = c.id
                WHERE c.id = :coachId
            """)
    List<SessionEntity> findSessionsByCoachId(Long coachId);

    @Query("""
                SELECT
                    p.id AS participant_id,
                    p.name,
                    p.email,
                    sp.registered_at
                FROM participants p
                JOIN session_participants sp ON p.id = sp.participant_id
                WHERE sp.session_id = :sessionId
                ORDER BY sp.registered_at ASC
            """)
    List<ParticipantRegistration> findParticipantsBySessionId(Long sessionId);

    @Modifying
    @Query("""
                INSERT INTO session_participants (session_id, participant_id, registered_at)
                VALUES (:sessionId, :participantId, NOW())
            """)
    boolean registerParticipant(Long sessionId, Long participantId);

    @Modifying
    @Query("""
                DELETE FROM session_participants
                WHERE session_id = :sessionId AND participant_id = :participantId
            """)
    boolean unregisterParticipant(Long sessionId, Long participantId);
}
