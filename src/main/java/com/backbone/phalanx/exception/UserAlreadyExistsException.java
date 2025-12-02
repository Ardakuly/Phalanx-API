package com.backbone.phalanx.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("Пользователь с именем пользователя: " + username + " уже существует.");
    }
}
