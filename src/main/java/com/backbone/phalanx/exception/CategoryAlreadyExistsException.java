package com.backbone.phalanx.exception;

public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException(String name) {
        super("Category already exists for name: " + name + ".");
    }
}
