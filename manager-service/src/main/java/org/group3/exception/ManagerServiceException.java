package org.group3.exception;

import lombok.Getter;

@Getter
public class ManagerServiceException extends RuntimeException{

    private final ErrorType errorType;

    public ManagerServiceException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ManagerServiceException(ErrorType errorType, String customMessage){
        super(customMessage);
        this.errorType = errorType;
    }
}
