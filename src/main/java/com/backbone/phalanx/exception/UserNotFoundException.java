package com.backbone.phalanx.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("Пользователь с именем пользователя: " + username + " не найден.");
    }
}
