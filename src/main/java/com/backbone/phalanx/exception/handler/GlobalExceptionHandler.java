package com.backbone.phalanx.exception.handler;

import com.backbone.phalanx.exception.ProductNotFoundException;
import com.backbone.phalanx.exception.ProductStockBalanceNotSufficient;
import com.backbone.phalanx.exception.RefreshTokenIsInvalid;
import com.backbone.phalanx.exception.UserIsNotEnabled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFound(ProductNotFoundException exception) {

        logException(exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(ProductStockBalanceNotSufficient.class)
    public ResponseEntity<?> handleProductNotFound(ProductStockBalanceNotSufficient exception) {

        logException(exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(UserIsNotEnabled.class)
    public ResponseEntity<?> handleProductNotFound(UserIsNotEnabled exception) {

        logException(exception);

        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(RefreshTokenIsInvalid.class)
    public ResponseEntity<?> handleProductNotFound(RefreshTokenIsInvalid exception) {

        logException(exception);

        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(Map.of("error", exception.getMessage()));
    }

    private void logException(Exception exception) {
        log.error("EXCEPTION THROWN: {}", exception.getMessage());
    }
}
