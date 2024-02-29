package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.group3.constant.ErrorMessages.*;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(102500,INTERNAL_ERROR_SERVER_ERROR_MESSAGES,HttpStatus.INTERNAL_SERVER_ERROR),

    COMMENT_NOT_PENDING(102010,COMMENT_NOT_PENDING_ERROR_MESSAGES,HttpStatus.BAD_REQUEST),

    COMMENT_NOT_FOUND(102011, COMMENT_NOT_FOUND_ERROR_MESSAGES, HttpStatus.BAD_REQUEST),

    PARAMETER_NOT_VALID(102008,PARAMETER_NOT_VALID_ERROR_MESSAGES,HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    final HttpStatus httpStatus;
}
