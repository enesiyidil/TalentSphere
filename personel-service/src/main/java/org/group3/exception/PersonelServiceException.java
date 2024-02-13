package org.group3.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PersonelServiceException extends RuntimeException{
    private final List<ErrorType> errorTypes;

    public PersonelServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorTypes = new ArrayList<>();
        this.errorTypes.add(errorType);
    }

    public PersonelServiceException(ErrorType errorType, String customMesaj) {
        super(customMesaj);
        this.errorTypes = new ArrayList<>();
        this.errorTypes.add(errorType);
    }

    public PersonelServiceException(List<ErrorType> errorTypes) {
        super("Birden Fazla Hata Meydana Geldi.");
        this.errorTypes = errorTypes;
    }


}
