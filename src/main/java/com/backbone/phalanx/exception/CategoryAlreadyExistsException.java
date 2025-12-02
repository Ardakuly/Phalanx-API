package com.backbone.phalanx.exception;

public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException(String name) {
        super("Категория с именем " + name + " уже существует.");
    }
}
