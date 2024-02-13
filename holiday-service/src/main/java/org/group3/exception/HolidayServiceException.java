package org.group3.exception;

import lombok.Getter;

@Getter
public class HolidayServiceException extends RuntimeException{

    private final ErrorType errorType;

    public HolidayServiceException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public HolidayServiceException(ErrorType errorType, String customMessage){
        super(customMessage);
        this.errorType = errorType;
    }
}
