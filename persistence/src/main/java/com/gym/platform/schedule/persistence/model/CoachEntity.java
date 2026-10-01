package com.gym.platform.schedule.persistence.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("coaches")
public record CoachEntity(
        @Id Long id,
        String email,
        String name) {
}
