package com.boot.springboot.exception;

public class SameUserInDatabase extends RuntimeException {
    public SameUserInDatabase(String message) {
        super(message);
    }
}
