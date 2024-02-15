package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),

    NOT_ACTIVE(2100, "Şirket lilinmiş", HttpStatus.NOT_FOUND),

    NOT_FOUND(2100, "Şirket yok", HttpStatus.NOT_FOUND),

    COMPANY_NOT_ACTIVE(2100, "Şirket lilinmiş", HttpStatus.NOT_FOUND),

    SHIFT_NOT_ACTIVE(2100, "Şirket lilinmiş", HttpStatus.NOT_FOUND),

    COMPANY_NOT_FOUND(2100, "Şirket yok", HttpStatus.NOT_FOUND),

    BREAK_NOT_ACTIVE(2100, "Şirket lilinmiş", HttpStatus.NOT_FOUND),
    COMMUNICATION_NOT_ACTIVE(2100, "Şirket lilinmiş", HttpStatus.NOT_FOUND),

    BREAK_NOT_FOUND(2100, "Şirket yok", HttpStatus.NOT_FOUND),
    COMMUNICATION_NOT_FOUND(2100, "Şirket yok", HttpStatus.NOT_FOUND),

    SHIFT_NOT_FOUND(2100, "Şirket yok", HttpStatus.NOT_FOUND),

    COMMUNICATION_ALREADY_EXISTS(2100, " yok", HttpStatus.BAD_REQUEST),
    HOLIDAY_ALREADY_EXISTS(2100, " yok", HttpStatus.BAD_REQUEST),

    SHIFT_ALREADY_EXISTS(2100, " yok", HttpStatus.BAD_REQUEST),

    PERSONAL_ALREADY_EXISTS(2100, " yok", HttpStatus.BAD_REQUEST),

    PAYMENT_ALREADY_EXISTS(2100, " yok", HttpStatus.BAD_REQUEST),

    BREAK_ALREADY_EXISTS(2100, " yok", HttpStatus.BAD_REQUEST),

    INVALID_TOKEN(6000, "Geçersiz token", HttpStatus.BAD_REQUEST),

    TOKEN_NOT_CREATE(6001, "Token oluşturulmadı", HttpStatus.BAD_REQUEST),

    PARAMETER_NOT_VALID(6001, "Token oluşturulmadı", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    final HttpStatus httpStatus;

}
