package org.group3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR_SERVER(5100,"Sunucu Hatası",HttpStatus.INTERNAL_SERVER_ERROR),

    PASSWORD_MISMATCH(2200,"Şifreler uyuşmuyor",HttpStatus.BAD_REQUEST),
    PARAMETER_NOT_VALID(5000,"Parametre Hatası",HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE(3001,"Hesabınız aktif değil",HttpStatus.FORBIDDEN),
    ACTIVATION_CODE_MISMATSC(3002,"Aktivasyon kodu hatalı",HttpStatus.BAD_REQUEST),




    REGISTER_PASSWORD_MISMATCH(1001,"The entered passwords do not match.",HttpStatus.BAD_REQUEST),
    REGISTER_EMAIL_ALREADY_EXISTS(1002,"This email address has already been registered.",HttpStatus.BAD_REQUEST),

    EMAIL_EXITS(2100,"Email is already in use.",HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(3000,"Incorrect email or password.",HttpStatus.NOT_FOUND),
    ID_NOT_FOUND(3000,"User not found.",HttpStatus.NOT_FOUND),
    USER_ALREADY_DELETED(3003,"This account has already been deleted.",HttpStatus.BAD_REQUEST),
    USERNAME_EXITS(3004,"Username is already in use.",HttpStatus.BAD_REQUEST),

    INVALID_TOKEN(6000,"Invalid token.",HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(6001,"Token could not be created.",HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    HttpStatus httpStatus;
}
