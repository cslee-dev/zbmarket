package com.example.zbmarket.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
    private final MarketErrorCode marketErrorCode;
    private final String detailMessage;

    public MemberException(MarketErrorCode errorCode) {
        super(errorCode.getMessage());
        this.marketErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

}
