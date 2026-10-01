package com.gym.platform.schedule.domain.model;

public record Participant(
                Long id,
                String email,
                String name) {
        public Participant withoutId() {
                return new Participant(null, this.name, this.email);
        }
}
