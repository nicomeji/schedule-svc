package com.gym.platform.schedule.domain.model;

public record Coach(
                Long id,
                String email,
                String name) {
        public Coach withoutId() {
                return new Coach(null, this.email, this.name);
        }
}
