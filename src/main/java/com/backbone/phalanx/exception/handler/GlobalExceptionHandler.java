package com.backbone.phalanx.exception.handler;

import com.backbone.phalanx.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

    @ExceptionHandler(ProductStockBalanceNotSufficientException.class)
    public ResponseEntity<?> handleProductNotFound(ProductStockBalanceNotSufficientException exception) {

        logException(exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(UserIsNotEnabledException.class)
    public ResponseEntity<?> handleProductNotFound(UserIsNotEnabledException exception) {

        logException(exception);

        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(RefreshTokenIsInvalidException.class)
    public ResponseEntity<?> handleProductNotFound(RefreshTokenIsInvalidException exception) {

        logException(exception);

        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleProductNotFound(UserNotFoundException exception) {

        logException(exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleProductNotFound(UserAlreadyExistsException exception) {

        logException(exception);

        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException exception) {
        logException(exception);
        return ResponseEntity.
                status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Неверные учетные данные"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleReportGeneration(ReportGenerationException exception) {
        logException(exception);
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", exception.getMessage()));
    }

    private void logException(Exception exception) {
        log.error("EXCEPTION THROWN: {}", exception.getMessage());
    }
}
