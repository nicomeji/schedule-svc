package com.gym.platform.schedule.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.gym.platform.schedule.persistence.model.CoachEntity;

public interface CoachRepository extends CrudRepository<CoachEntity, Long> {
}
