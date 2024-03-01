package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.group3.constant.ErrorMessages.*;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(93500,INTERNAL_ERROR_SERVER_ERROR_MESSAGES,HttpStatus.INTERNAL_SERVER_ERROR),

    EMAIL_OR_PHONE_EXITS(93009,EMAIL_OR_PHONE_EXITS_ERROR_MESSAGES,HttpStatus.BAD_REQUEST),

    ID_NOT_FOUND(93005,ID_NOT_FOUND_ERROR_MESSAGES,HttpStatus.NOT_FOUND),

    USER_ALREADY_DELETED(93004,USER_ALREADY_DELETED_ERROR_MESSAGES,HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(93003,USER_NOT_FOUND_ERROR_MESSAGES,HttpStatus.NOT_FOUND),

    PARAMETER_NOT_VALID(93008,PARAMETER_NOT_VALID_ERROR_MESSAGES,HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    final HttpStatus httpStatus;
}
