package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(5100,"Sunucu Hatası",HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_EXITS(2100,"Email kullanılıyor",HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH(2200,"Şifreler uyuşmuyor",HttpStatus.BAD_REQUEST),
    PARAMETER_NOT_VALID(5000,"Parametre Hatası",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(3000,"Email ya da Şifre hatalı veya eksik",HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_ACTIVE(3001,"Hesabınız aktif değil",HttpStatus.FORBIDDEN),
    ACTIVATION_CODE_MISMATSC(3002,"Aktivasyon kodu hatalı",HttpStatus.BAD_REQUEST),
    USER_ALREADY_DELETED(3003,"Hesap zaten silinmiş",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(6000,"geçersiz token",HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(6001,"token oluşturulamadı",HttpStatus.BAD_REQUEST),
    EMAIL_OR_PHONE_EXITS(2100,"Email veya telefon kullanılıyor",HttpStatus.BAD_REQUEST),
    ID_NOT_FOUND(3000,"User not found.",HttpStatus.NOT_FOUND);



    private int code;
    private String message;
    HttpStatus httpStatus;
}
