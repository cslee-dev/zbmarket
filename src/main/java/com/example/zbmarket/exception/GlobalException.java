package com.example.zbmarket.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final ErrorCodeEnum errorCodeEnum;
    private final String detailMessage;

    public GlobalException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCodeEnum = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

}
