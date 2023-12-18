package com.example.zbmarket.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    INTERNAL_SERVER_ERROR("서버에러"),
    NOT_FOUND_MEMBER_ERROR("멤버 정보가 없습니다."),
    JOINED_MEMBER_ERROR("이미 가입된 멤버입니다."),
    NOT_FOUND_PRODUCT_ERROR("상품 정보가 없습니다."),
    FIELD_NULL_ERROR, INVALID_EMAIL_FORMAT, FIELD_SIZE_ERROR,
    PATTERN_MISMATCH_ERROR, UNKNOWN_FIELD_ERROR,
    ORDER_OWNER_ERROR("권한이 없습니다.");
    private final String message;

    ErrorCodeEnum(String message_in) {
        message = message_in;
    }

    ErrorCodeEnum() {
        message = "";
    }
}
