package com.botscrew.universitymanager.exception;

public class LectorIsAlreadyHeadException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Lector is already a head of department";

    public LectorIsAlreadyHeadException(String message) {
        super(message.isEmpty() ? DEFAULT_MESSAGE : message);
    }

    public LectorIsAlreadyHeadException() {
        super(DEFAULT_MESSAGE);
    }
}
