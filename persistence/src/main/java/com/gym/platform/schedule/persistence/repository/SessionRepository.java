package com.gym.platform.schedule.persistence.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.gym.platform.schedule.persistence.model.SessionEntity;
import com.gym.platform.schedule.persistence.projection.SessionParticipants;

public interface SessionRepository extends SearchSessionRepository, CrudRepository<SessionEntity, Long> {
    @Query("""
                SELECT s.*
                FROM sessions s
                JOIN coaches c ON s.coach_id = c.id
                WHERE c.id = :coachId
            """)
    List<SessionEntity> findSessionsByCoachId(Long coachId);

    @Query("""
                SELECT s.*
                FROM sessions s
                JOIN session_participants sp ON s.id = sp.session_id
                WHERE sp.participant_id = :participantId
                ORDER BY s.start_time DESC
            """)
    List<SessionEntity> findSessionsByParticipantId(Long participantId);

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

    @Query("""
                SELECT sp.participant_id, sp.registered_at
                FROM session_participants sp
                WHERE sp.session_id = :sessionId
            """)
    List<SessionParticipants> findSessionParticipantsBySessionId(Long sessionId);
}
