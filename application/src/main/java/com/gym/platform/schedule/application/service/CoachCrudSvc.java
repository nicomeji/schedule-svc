package com.gym.platform.schedule.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gym.platform.schedule.domain.model.Coach;
import com.gym.platform.schedule.domain.repo.CoachRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CoachCrudSvc {
    private final CoachRepo repo;

    public Coach create(Coach.BaseData coachData) {
        return repo.create(coachData);
    }

    public Optional<Coach> retrieve(Long id) {
        return repo.retrieve(id);
    }
}
