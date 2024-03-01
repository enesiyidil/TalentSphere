package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.group3.constant.ErrorMessages.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorType {

    INTERNAL_SERVER_ERROR(95500,INTERNAL_ERROR_SERVER_ERROR_MESSAGES,HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(95003,USER_NOT_FOUND_ERROR_MESSAGES,HttpStatus.NOT_FOUND),
    PARAMETER_NOT_VALID(95008,PARAMETER_NOT_VALID_ERROR_MESSAGES,HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
