package com.gym.platform.schedule.domain.exceptions;

import lombok.Getter;

public abstract class BusinessException extends RuntimeException {
    @Getter
    private final String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public static class NotFouncException extends BusinessException {
        public NotFouncException() {
            super("Resource not found.", "MISSING_RESOURCE");
        }
    }

    public static class SessionOverlapException extends BusinessException {
        public SessionOverlapException() {
            super("The coach already has a session scheduled that overlaps with this time slot.", "SESSION_OVERLAP");
        }
    }

    public static class SessoinCapacityExceededException extends BusinessException {
        public SessoinCapacityExceededException() {
            super("The session cannot acept more participants.", "EXCEEDED_SESSION_REGISTRATION");
        }
    }

    public static class DuplicateSessionRegistrationException extends BusinessException {
        public DuplicateSessionRegistrationException() {
            super("The participant is already registered in the session.", "DUPLICATE_SESSION_REGISTRATION");
        }
    }
}
