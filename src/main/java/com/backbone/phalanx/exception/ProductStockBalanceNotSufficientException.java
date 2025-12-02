package com.backbone.phalanx.exception;

public class ProductStockBalanceNotSufficientException extends RuntimeException {

    public ProductStockBalanceNotSufficientException(String name) {
        super("Количество продукта с именем " + name + " на складе недостаточно.");
    }
}
