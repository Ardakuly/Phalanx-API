package com.backbone.phalanx.exception;

public class StockFrozenException extends RuntimeException {
    public StockFrozenException() {
        super("Операций заблокированы во время инвентаризаций.");
    }
}
