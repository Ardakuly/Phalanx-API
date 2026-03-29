package com.backbone.phalanx.exception;

public class ReturnQuantityExceededException extends RuntimeException {

    public ReturnQuantityExceededException(String name) {
        super("Количество возвращенных товаров превысило проданные количество товаров для: " + name + ".");
    }
}
