package org.group3.exception;

import lombok.Getter;

@Getter
public class ApiGatewayManagerException extends RuntimeException{

    private final ErrorType errorType;

    public ApiGatewayManagerException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ApiGatewayManagerException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}
