package com.daker.util.exception;

import com.daker.util.ApiResponse;
import com.daker.util.code.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.onFailure(
                        e.getErrorCode().getCode(),
                        e.getErrorCode().getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        e.printStackTrace();

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR_500.getStatus())
                .body(ApiResponse.onFailure(
                        ErrorCode.INTERNAL_SERVER_ERROR_500.getCode(),
                        ErrorCode.INTERNAL_SERVER_ERROR_500.getMessage()
                ));
    }
}