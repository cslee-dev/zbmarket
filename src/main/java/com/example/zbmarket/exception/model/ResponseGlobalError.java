package com.example.zbmarket.exception.model;


import com.example.zbmarket.exception.ErrorCodeEnum;
import com.example.zbmarket.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseGlobalError {
    private final ErrorCodeEnum errorCode;
    private final String errorMessage;

    public static ResponseGlobalError from(GlobalException e) {
        return ResponseGlobalError.builder()
                .errorCode(e.getErrorCodeEnum())
                .errorMessage(e.getMessage())
                .build();
    }

    public static ResponseGlobalError from(Exception e) {
        return ResponseGlobalError.builder()
                .errorCode(ErrorCodeEnum.INTERNAL_SERVER_ERROR)
                .errorMessage(e.getMessage())
                .build();
    }
}
