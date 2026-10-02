package com.gym.platform.schedule.application.exceptions;

import lombok.Getter;

public abstract class BusinessException extends RuntimeException {
    @Getter
    private final String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public static class SessionOverlapException extends BusinessException {
        public SessionOverlapException() {
            super("The coach already has a session scheduled that overlaps with this time slot.", "SESSION_OVERLAP");
        }
    }
}
