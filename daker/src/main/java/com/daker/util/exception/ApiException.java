package com.daker.util.exception;

import com.daker.util.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public ApiException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
