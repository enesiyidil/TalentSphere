package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.group3.constant.ErrorMessages.*;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(98500, INTERNAL_ERROR_SERVER_ERROR_MESSAGES, HttpStatus.INTERNAL_SERVER_ERROR),

    PAYMENT_NOT_ACTIVE(98021, PAYMENT_NOT_ACTIVE_ERROR_MESSAGES, HttpStatus.FORBIDDEN),

    PAYMENT_NOT_FOUND(98022, PAYMENT_NOT_FOUND_ERROR_MESSAGES, HttpStatus.NOT_FOUND),

    PAYMENT_ALREADY_MADE(98023, PAYMENT_ALREADY_MADE_ERROR_MESSAGES, HttpStatus.BAD_REQUEST),

    PARAMETER_NOT_VALID(98008,PARAMETER_NOT_VALID_ERROR_MESSAGES,HttpStatus.BAD_REQUEST);


    private final int code;
    private final String message;
    final HttpStatus httpStatus;
}
