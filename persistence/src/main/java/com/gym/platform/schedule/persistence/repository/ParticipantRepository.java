package com.gym.platform.schedule.persistence.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.gym.platform.schedule.persistence.model.ParticipantEntity;
import com.gym.platform.schedule.persistence.model.SessionEntity;

public interface ParticipantRepository extends CrudRepository<ParticipantEntity, Long> {
    @Query("""
                SELECT s.*
                FROM sessions s
                JOIN session_participants sp ON s.id = sp.session_id
                WHERE sp.participant_id = :participantId
            """)
    List<SessionEntity> findSessionsByParticipantId(Long participantId);
}
