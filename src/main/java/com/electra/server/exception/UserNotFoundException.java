package com.electra.server.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super("User " + message + " not found");
    }

}
