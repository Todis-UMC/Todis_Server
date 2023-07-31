package com.todis.todisweb.global.exception;

import com.todis.todisweb.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;


}