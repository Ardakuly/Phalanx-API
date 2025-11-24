package com.backbone.phalanx.exception;

public class UserIsNotEnabledException extends RuntimeException {

    public UserIsNotEnabledException(String username) {
        super("User with username: " + username + " is not enabled.");
    }
}
