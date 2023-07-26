package com.todis.todisweb.global.exception;

<<<<<<< Updated upstream
=======

import com.todis.todisweb.global.response.ErrorCode;
>>>>>>> Stashed changes
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
<<<<<<< Updated upstream

    private final ErrorCode status;
=======
    private final ErrorCode errorCode;
>>>>>>> Stashed changes
}
