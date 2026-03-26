package com.backbone.phalanx.exception;

public class StockFrozenException extends RuntimeException {
    public StockFrozenException() {
        super("STOCK_FROZEN_DUE_TO_INVENTARIZATION");
    }
}
