package com.todis.todisweb.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, " Method Not Allowed "),
    ENTITY_NOT_FOUND(400, " Entity Not Found"),
    INVALID_TYPE_VALUE(400, "Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "Access is Denied"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    ALREADY_EXISTS(400, "이미 존재하는 사용자입니다."),
    INVALID_PASSWORD(400, "아이디 또는 비밀번호가 틀립니다."),
    TOKEN_EXPIRED(400, "토큰이 만료되었습니다."),
    TOKEN_INVALID(400, "토큰이 유효하지 않습니다."),
    EMAIL_ALREADY_USED(400,"이미 사용중인 이메일 입니다."),
    UNMATCHED_PASSWORD(400, "비밀번호가 틀렸습니다."),

    // FriendRequest
    REQUEST_NOT_EXISTS(400, "해당 친구 요청이 존재하지 않습니다."),
    REAUEST_ALREADY_EXISTS(400, "해당 친구 요청이 이미 존재합니다."),

    ;

    private final int code;
    private final String message;
}