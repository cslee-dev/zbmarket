package com.example.zbmarket.exception.model;

import com.example.zbmarket.exception.ValidateErrorCode;
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
    private ValidateErrorCode errorCode;
    private String errorMessage;

    public static ResponseValidateItemError from(ObjectError e){
        ValidateErrorCode errorCode = mapToErrorCode(e);
        return ResponseValidateItemError.builder()
                .errorCode(errorCode)
                .errorMessage(e.getDefaultMessage())
                .build();
    }

    public static ValidateErrorCode mapToErrorCode(ObjectError error) {
        ValidateErrorCode errorCode;
        if(error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            switch (Objects.requireNonNull(fieldError.getCode())) {
                case "NotNull":
                    errorCode = ValidateErrorCode.FIELD_NULL_ERROR;
                    break;
                case "Email":
                    errorCode = ValidateErrorCode.INVALID_EMAIL_FORMAT;
                    break;
                case "Size":
                    errorCode = ValidateErrorCode.FIELD_SIZE_ERROR;
                    break;
                case "Pattern":
                    errorCode = ValidateErrorCode.PATTERN_MISMATCH_ERROR;
                    break;
                default:
                    errorCode = ValidateErrorCode.UNKNOWN_FIELD_ERROR;
            }
        } else {
            errorCode = ValidateErrorCode.FIELD_NULL_ERROR;
        }
        return errorCode;
    }
}
