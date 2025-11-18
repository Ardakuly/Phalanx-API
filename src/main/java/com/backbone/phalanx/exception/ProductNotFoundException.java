package com.backbone.phalanx.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String externalId) {
        super("Product not found for external id: " + externalId + ".");
    }
}
