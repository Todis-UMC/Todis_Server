package com.todis.todisweb.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, " Entity Not Found"),
    INVALID_TYPE_VALUE(400, "Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, " is Denied"),

    INTERNAL_SERVER_ERROR(500, "Server Error"),
    ALREADY_EXISTS(404, "이미 존재한 사용자입니다.");

    private final int status;
    private final String message;
}