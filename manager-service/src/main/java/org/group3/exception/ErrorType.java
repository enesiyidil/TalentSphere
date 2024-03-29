package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.group3.constant.ErrorMessages.*;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(94500, INTERNAL_ERROR_SERVER_ERROR_MESSAGES, HttpStatus.INTERNAL_SERVER_ERROR),

    MANAGER_NOT_ACTIVE(94015, MANAGER_NOT_ACTIVE_ERROR_MESSAGES, HttpStatus.FORBIDDEN),

    MANAGER_NOT_FOUND(94016, MANAGER_NOT_FOUND_ERROR_MESSAGES, HttpStatus.NOT_FOUND),

    COMPANY_ALREADY_EXISTS(94017, COMPANY_ALREADY_EXISTS_ERROR_MESSAGES, HttpStatus.BAD_REQUEST),

    COMPANY_NOT_REGISTERED(94018, COMPANY_NOT_REGISTERED_ERROR_MESSAGES, HttpStatus.BAD_REQUEST),

    PERSONAL_ALREADY_EXISTS(94019, PERSONAL_ALREADY_EXISTS_ERROR_MESSAGES, HttpStatus.BAD_REQUEST),

    PERSONAL_NOT_REGISTERED(94020, PERSONAL_NOT_REGISTERED_ERROR_MESSAGES, HttpStatus.BAD_REQUEST),

    PARAMETER_NOT_VALID(94008,PARAMETER_NOT_VALID_ERROR_MESSAGES,HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    final HttpStatus httpStatus;
}
