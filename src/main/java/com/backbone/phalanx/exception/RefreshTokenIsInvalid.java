package com.backbone.phalanx.exception;

public class RefreshTokenIsInvalid extends RuntimeException {

    public RefreshTokenIsInvalid(String username) {
        super("User with username: " + username + " invalid refresh token");
    }
}
