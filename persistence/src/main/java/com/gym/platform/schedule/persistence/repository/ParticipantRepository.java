package com.gym.platform.schedule.persistence.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.gym.platform.schedule.persistence.model.ParticipantEntity;

public interface ParticipantRepository extends CrudRepository<ParticipantEntity, Long> {
    @Query("""
                SELECT
                    p.id,
                    p.name,
                    p.email
                FROM participants p
                JOIN session_participants sp ON p.id = sp.participant_id
                WHERE sp.session_id = :sessionId
                ORDER BY sp.registered_at ASC
            """)
    List<ParticipantEntity> findParticipantsBySessionId(Long sessionId);
}
