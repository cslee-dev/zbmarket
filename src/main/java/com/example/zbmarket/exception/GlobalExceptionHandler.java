package com.example.zbmarket.exception;

import com.example.zbmarket.exception.model.ResponseGlobalError;
import com.example.zbmarket.exception.model.ResponseValidateError;
import com.example.zbmarket.exception.model.ResponseValidateItemError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseValidateError> handleException(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        log.error("message: {}, url: {}, ", e.getMessage(), request.getRequestURI());
        List<ResponseValidateItemError> errorItems = e.getBindingResult()
                .getAllErrors().stream()
                .map(ResponseValidateItemError::from).collect(Collectors.toList());
        ResponseValidateError errorResponse = new ResponseValidateError(errorItems);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({GlobalException.class, Exception.class})
    public ResponseEntity<ResponseGlobalError> handleException(
            GlobalException e,
            HttpServletRequest request
    ) {
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDetailMessage(), request.getRequestURI(), e.getDetailMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseGlobalError.from(e));
    }

}
