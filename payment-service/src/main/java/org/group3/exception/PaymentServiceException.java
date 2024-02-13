package org.group3.exception;

import lombok.Getter;

@Getter
public class PaymentServiceException extends RuntimeException{

    private final ErrorType errorType;

    public PaymentServiceException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public PaymentServiceException(ErrorType errorType, String customMessage){
        super(customMessage);
        this.errorType = errorType;
    }
}
