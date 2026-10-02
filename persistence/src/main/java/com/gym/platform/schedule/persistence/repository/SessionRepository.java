package com.gym.platform.schedule.persistence.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.gym.platform.schedule.persistence.model.SessionEntity;

public interface SessionRepository extends SearchSessionRepository, CrudRepository<SessionEntity, Long> {
    @Query("""
                SELECT s.*
                FROM sessions s
                JOIN coaches c ON s.coach_id = c.id
                WHERE c.id = :coachId
            """)
    List<SessionEntity> findSessionsByCoachId(Long coachId);

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
