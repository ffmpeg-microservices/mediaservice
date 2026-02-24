package com.mediaalterations.mediaservice.exception;

public class ProcessKillException extends RuntimeException {
    public ProcessKillException(String message) {
        super(message);
    }

    public ProcessKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
