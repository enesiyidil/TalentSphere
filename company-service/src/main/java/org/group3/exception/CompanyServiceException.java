package org.group3.exception;

import lombok.Getter;

@Getter
public class CompanyServiceException extends RuntimeException{

    private final ErrorType errorType;

    public CompanyServiceException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public CompanyServiceException(ErrorType errorType, String customMessage){
        super(customMessage);
        this.errorType = errorType;
    }
}
