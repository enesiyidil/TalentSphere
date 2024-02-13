package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),

    PAYMENT_NOT_ACTIVE(3001, "Manager aktif değil", HttpStatus.FORBIDDEN),

    PAYMENT_NOT_FOUND(3002, "Manager bulunamadı", HttpStatus.NOT_FOUND),

    PAYMENT_ALREADY_MADE(3002, "Şirket zaten var", HttpStatus.BAD_REQUEST),

    COMPANY_NOT_REGISTERED(3002, "Şirket kayıtlı değil", HttpStatus.BAD_REQUEST),

    PERSONAL_ALREADY_EXISTS(3002, "Personal zaten var", HttpStatus.BAD_REQUEST),

    PERSONAL_NOT_REGISTERED(3002, "Personal kayıtlı değil", HttpStatus.BAD_REQUEST),

    INVALID_TOKEN(6000,"Geçersiz token",HttpStatus.BAD_REQUEST),

    TOKEN_NOT_CREATE(6001,"Token oluşturulmadı",HttpStatus.BAD_REQUEST),
    PARAMETER_NOT_VALID(6001,"PARAMETER_NOT_VALID",HttpStatus.BAD_REQUEST);


    private final int code;
    private final String message;
    final HttpStatus httpStatus;
}
