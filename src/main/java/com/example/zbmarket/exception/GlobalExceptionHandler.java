package com.example.zbmarket.exception;

import com.example.zbmarket.exception.model.ResponseMemberError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ResponseMemberError> handleException(
            MemberException e,
            HttpServletRequest request
    ) {
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDetailMessage(), request.getRequestURI(), e.getDetailMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMemberError.from(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("errorMessage: {}, url: {}", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                e.getMessage()
        );
    }
}
