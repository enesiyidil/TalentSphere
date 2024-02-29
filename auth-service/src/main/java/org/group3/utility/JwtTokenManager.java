package org.group3.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.group3.entity.Enums.ERole;
import org.group3.exception.AuthManagerException;
import org.group3.exception.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {

    @Value("${authservice.secrets.secret-key}")
    String secretKey;
    @Value("${authservice.secrets.issuer}")
    String issuer;

    private final Long expirationTime=1000L*60*15;

    public Optional<String> createToken(Long id, ERole role){
        String token=null;
        Date date=new Date(System.currentTimeMillis()+expirationTime);
        try{
            token= JWT.create()
                    .withClaim("id",id)
                    .withClaim("role",role.toString())
                    .withIssuer(issuer)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC512(secretKey));
        }catch (Exception e){
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }
        return Optional.ofNullable(token);
    }

    public Optional<String> createToken(Long id, ERole role, String code){
        String token=null;
        Date date=new Date(System.currentTimeMillis()+expirationTime);
        try{
            token= JWT.create()
                    .withClaim("id",id)
                    .withClaim("role",role.toString())
                    .withClaim("code",code)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC512(secretKey));
        }catch (Exception e){
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }
        return Optional.ofNullable(token);
    }
    public Optional<Long> decodeToken(String token){
        try {
            Algorithm algorithm=Algorithm.HMAC512(secretKey);
            JWTVerifier verifier=JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT==null){
                return Optional.empty();
            }
            Long id = decodedJWT.getClaim("id").asLong();
            return Optional.of(id);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
