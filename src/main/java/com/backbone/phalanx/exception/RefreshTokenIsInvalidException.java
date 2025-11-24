package com.backbone.phalanx.exception;

public class RefreshTokenIsInvalidException extends RuntimeException {

    public RefreshTokenIsInvalidException(String username) {
        super("User with username: " + username + " invalid refresh token");
    }
}
