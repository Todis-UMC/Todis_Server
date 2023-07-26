package com.todis.todisweb.global.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

import static com.todis.todisweb.global.exception.ErrorCode.*;


@RestControllerAdvice()
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        LOGGER.error("handleMethodArgumentNotValidException", e);
        return new ResponseEntity(new ErrorResponse(INVALID_INPUT_VALUE.getStatus(),
                INVALID_INPUT_VALUE.getMessage()), HttpStatus.BAD_REQUEST);
    }

<<<<<<< Updated upstream

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity handleBindException(BindException e) {
        LOGGER.error("handleBindException", e);
        return new ResponseEntity(new ErrorResponse(INVALID_INPUT_VALUE.getStatus(),
                INVALID_INPUT_VALUE.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        LOGGER.error("handleHttpRequestMethodNotSupportedException", e);
        return new ResponseEntity(
                new ErrorResponse(METHOD_NOT_ALLOWED.getStatus(), METHOD_NOT_ALLOWED.getMessage()),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        LOGGER.error("handleAccessDeniedException", e);
        return new ResponseEntity<>(new ErrorResponse(HANDLE_ACCESS_DENIED.getStatus(),
                HANDLE_ACCESS_DENIED.getMessage()),
                HttpStatus.valueOf(HANDLE_ACCESS_DENIED.getStatus()));
    }

}
=======
    @ExceptionHandler({ CustomException.class })
    protected ResponseForm handleCustomException(CustomException ex) {
        LOGGER.error("커스텀에러", ex);
        return ResponseForm.error(ex.getErrorCode().getCode(), ex.getErrorCode().getMessage());
    }
}
>>>>>>> Stashed changes
