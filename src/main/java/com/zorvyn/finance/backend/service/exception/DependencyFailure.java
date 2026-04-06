package com.zorvyn.finance.backend.service.exception;

public class DependencyFailure extends RuntimeException {

    public DependencyFailure(String message) {
        super(message);
    }
}
