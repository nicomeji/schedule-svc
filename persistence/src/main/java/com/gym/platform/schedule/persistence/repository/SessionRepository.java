package com.gym.platform.schedule.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.gym.platform.schedule.persistence.model.SessionEntity;
import com.gym.platform.schedule.persistence.projection.ParticipantRegistration;
import com.gym.platform.schedule.persistence.projection.SessionLocation;

public interface SessionRepository extends SearchSessionRepository, CrudRepository<SessionEntity, Long> {
    @Query("""
                SELECT
                    s.id AS session_id,
                    s.coach_id AS coach_id,
                    s.start_time AS start_time,
                    s.end_time AS end_time,
                    s.capacity AS capacity,
                    l.id AS location_id,
                    l.name AS name,
                    l.time_zone AS time_zone
                FROM sessions s
                JOIN locations l ON s.location_id = l.id
                WHERE s.id = :id
            """)
    Optional<SessionLocation> findSessionLocationById(Long id);

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
    List<ParticipantRegistration> findParticipantRegistrationsBySessionId(Long sessionId);

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
