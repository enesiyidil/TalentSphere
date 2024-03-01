package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.group3.constant.ErrorMessages.*;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(101500, INTERNAL_ERROR_SERVER_ERROR_MESSAGES, HttpStatus.INTERNAL_SERVER_ERROR),

    HOLIDAY_NOT_ACTIVE(101012, HOLIDAY_NOT_ACTIVE_ERROR_MESSAGES, HttpStatus.FORBIDDEN),

    HOLIDAY_NOT_FOUND(101013, HOLIDAY_NOT_FOUND_ERROR_MESSAGES, HttpStatus.NOT_FOUND),

    HOLIDAY_NOT_PENDING(101014,HOLIDAY_NOT_PENDING_ERROR_MESSAGES,HttpStatus.BAD_REQUEST),

    PARAMETER_NOT_VALID(101008,PARAMETER_NOT_VALID_ERROR_MESSAGES,HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    final HttpStatus httpStatus;
}
