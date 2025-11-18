package com.backbone.phalanx.exception;

public class ProductStockBalanceNotSufficient extends RuntimeException {

    public ProductStockBalanceNotSufficient(String name) {
        super("Product's quantity in stock with name: " + name + " not sufficient.");
    }
}
