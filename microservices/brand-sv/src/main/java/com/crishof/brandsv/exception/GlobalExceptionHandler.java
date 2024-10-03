package com.crishof.brandsv.exception;

import com.crishof.brandsv.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleInvoiceNotFoundException(BrandNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage()));
    }

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicateNameException(DuplicateNameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(e.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(ProductsAssociatedException.class)
    public ResponseEntity<ApiResponse<String>> handleProductsAssociatedException(ProductsAssociatedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(e.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Data integrity violation: " + e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Internal server error: " + e.getMessage()));
    }
}