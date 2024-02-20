package org.group3.exception;

import lombok.Getter;

@Getter
public class CommentManagerException extends RuntimeException{

    private final ErrorType errorType;

    public CommentManagerException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public CommentManagerException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}
