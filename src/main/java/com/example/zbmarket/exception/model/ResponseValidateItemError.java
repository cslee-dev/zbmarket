package com.example.zbmarket.exception.model;

import com.example.zbmarket.exception.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ResponseValidateItemError {
    private ErrorCodeEnum errorCode;
    private String errorMessage;

    public static ResponseValidateItemError from(ObjectError e) {
        ErrorCodeEnum errorCode = mapToErrorCode(e);
        return ResponseValidateItemError.builder()
                .errorCode(errorCode)
                .errorMessage(e.getDefaultMessage())
                .build();
    }

    public static ErrorCodeEnum mapToErrorCode(ObjectError error) {
        ErrorCodeEnum errorCode;
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            switch (Objects.requireNonNull(fieldError.getCode())) {
                case "NotNull":
                    errorCode = ErrorCodeEnum.FIELD_NULL_ERROR;
                    break;
                case "Email":
                    errorCode = ErrorCodeEnum.INVALID_EMAIL_FORMAT;
                    break;
                case "Size":
                    errorCode = ErrorCodeEnum.FIELD_SIZE_ERROR;
                    break;
                case "Pattern":
                    errorCode = ErrorCodeEnum.PATTERN_MISMATCH_ERROR;
                    break;
                default:
                    errorCode = ErrorCodeEnum.UNKNOWN_FIELD_ERROR;
            }
        } else {
            errorCode = ErrorCodeEnum.FIELD_NULL_ERROR;
        }
        return errorCode;
    }
}
