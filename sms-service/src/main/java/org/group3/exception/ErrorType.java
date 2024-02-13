package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),

    SMS_SENDER_ERROR(3002, "Şirket zaten var", HttpStatus.BAD_REQUEST),

    INVALID_TOKEN(6000,"Geçersiz token",HttpStatus.BAD_REQUEST),

    TOKEN_NOT_CREATE(6001,"Token oluşturulmadı",HttpStatus.BAD_REQUEST);


    private final int code;
    private final String message;
    final HttpStatus httpStatus;
}
