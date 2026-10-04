package com.gym.platform.schedule.domain.repo;

import java.util.Optional;

import com.gym.platform.schedule.domain.model.Coach;

public interface CoachRepo {
    Coach create(Coach.BaseData coachData);

    Optional<Coach> retrieve(Long id);
}
