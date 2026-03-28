package com.backbone.phalanx.exception;

public class ReturnQuantityExceededException extends RuntimeException {

    public ReturnQuantityExceededException(String name) {
        super("Returned quantity exceeds sold quantity for product: " + name);
    }
}
