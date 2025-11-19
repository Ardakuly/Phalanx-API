package com.backbone.phalanx.exception;

public class UserIsNotEnabled extends RuntimeException {

    public UserIsNotEnabled(String username) {
        super("User with username: " + username + " is not enabled.");
    }
}
