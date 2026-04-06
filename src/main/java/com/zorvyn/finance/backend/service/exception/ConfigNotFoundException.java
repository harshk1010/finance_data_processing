package com.zorvyn.finance.backend.service.exception;

public class ConfigNotFoundException extends RuntimeException {

    public ConfigNotFoundException(String message) {
        super(message);
    }
}
