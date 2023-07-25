package com.todis.todisweb.global.exception;


import com.todis.todisweb.global.response.ResponseForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.todis.todisweb.global.response.ErrorCode.*;


@RestControllerAdvice()
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseForm handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        LOGGER.error("ArgumentNotValidException", e);
        return ResponseForm.error(INVALID_INPUT_VALUE.getCode(), INVALID_INPUT_VALUE.getMessage());
    }
}