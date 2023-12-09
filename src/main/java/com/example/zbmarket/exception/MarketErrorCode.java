package com.example.zbmarket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MarketErrorCode {
    JOINED_MEMBER_ERROR("이미 가입된 유저입니다.");
    private final String message;
}
