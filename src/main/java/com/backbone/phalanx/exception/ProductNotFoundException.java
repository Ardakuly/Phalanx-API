package com.backbone.phalanx.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String externalId) {
        super("Продукт с внешним идентификатором " + externalId + " не найден.");
    }
}
