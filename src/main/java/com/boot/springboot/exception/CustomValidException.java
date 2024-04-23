package com.boot.springboot.exception;

public class CustomValidException extends RuntimeException {
    String message;

    public CustomValidException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Problem with validation! Exception: " + message;
    }
}
