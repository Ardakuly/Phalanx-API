package com.backbone.phalanx.exception;

public class RefreshTokenIsInvalidException extends RuntimeException {

    public RefreshTokenIsInvalidException(String username) {
        super("Пользователь с именем пользователя: " + username + " имеет недействительный refresh token.");
    }
}
