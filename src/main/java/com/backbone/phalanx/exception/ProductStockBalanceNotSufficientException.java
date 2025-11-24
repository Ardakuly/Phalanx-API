package com.backbone.phalanx.exception;

public class ProductStockBalanceNotSufficientException extends RuntimeException {

    public ProductStockBalanceNotSufficientException(String name) {
        super("Product's quantity in stock with name: " + name + " not sufficient.");
    }
}
