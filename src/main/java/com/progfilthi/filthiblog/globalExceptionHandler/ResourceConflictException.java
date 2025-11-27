package com.progfilthi.filthiblog.globalExceptionHandler;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}
