package com.backbone.phalanx.exception;

public class UserIsNotEnabledException extends RuntimeException {

    public UserIsNotEnabledException(String username) {
        super("Пользователь с именем пользователя: " + username + " не активирован.");
    }
}
