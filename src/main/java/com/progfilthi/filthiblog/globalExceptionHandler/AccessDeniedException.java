package com.progfilthi.filthiblog.globalExceptionHandler;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
