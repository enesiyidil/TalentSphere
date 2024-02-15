package org.group3.exception;

import lombok.Getter;

@Getter
public class VisitorManagerException extends RuntimeException{

    private final ErrorType errorType;

    public VisitorManagerException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public VisitorManagerException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}
