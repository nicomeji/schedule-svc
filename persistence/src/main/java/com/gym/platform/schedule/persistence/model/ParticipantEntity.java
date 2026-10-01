package com.gym.platform.schedule.persistence.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("participants")
public record ParticipantEntity(
        @Id Long id,
        String email,
        String name) {
}
