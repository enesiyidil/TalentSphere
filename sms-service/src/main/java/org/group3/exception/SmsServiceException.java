package org.group3.exception;

import lombok.Getter;

@Getter
public class SmsServiceException extends RuntimeException{

    private final ErrorType errorType;

    public SmsServiceException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public SmsServiceException(ErrorType errorType, String customMessage){
        super(customMessage);
        this.errorType = errorType;
    }
}
