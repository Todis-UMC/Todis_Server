package com.todis.todisweb.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    JOIN_SUCCESS(200, "Join success!"),
    LOGIN_SUCCESS(200, "Login success!"),
    FIND_EMAIL(200, "Find Email!"),
    FIND_PASSWORD(200, "Find Password!"),

    ;
    // 다른 성공 코드들...

    private final int code;
    private final String message;

    }

