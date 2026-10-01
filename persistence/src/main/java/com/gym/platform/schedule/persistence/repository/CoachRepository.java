package com.gym.platform.schedule.persistence.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.gym.platform.schedule.persistence.model.CoachEntity;
import com.gym.platform.schedule.persistence.model.SessionEntity;

public interface CoachRepository extends CrudRepository<CoachEntity, Long> {
    @Query("""
                SELECT s.*
                FROM sessions s
                JOIN coaches c ON s.coach_id = c.id
                WHERE c.id = :coachId
            """)
    List<SessionEntity> findSessionsByCoachId(Long coachId);
}
