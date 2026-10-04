package com.gym.platform.schedule.domain.common;

public record SearchedPage(
        long offset,
        int size) {

    public SearchedPage {
        if (offset < 0) {
            throw new IllegalArgumentException("offset cannot be negative");
        }
        if (size < 10) {
            throw new IllegalArgumentException("min size is 10");
        }
        if (size > 100) {
            throw new IllegalArgumentException("max size is 100");
        }
    }
}
