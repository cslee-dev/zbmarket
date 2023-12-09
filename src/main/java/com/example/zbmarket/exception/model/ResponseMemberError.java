package com.example.zbmarket.exception.model;


import com.example.zbmarket.exception.MarketErrorCode;
import com.example.zbmarket.exception.MemberException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseMemberError {
    private final MarketErrorCode errorCode;
    private final String errorMessage;

    public static ResponseMemberError from(MemberException e) {
        return ResponseMemberError.builder()
                .errorCode(e.getMarketErrorCode())
                .errorMessage(e.getMessage())
                .build();
    }
}
