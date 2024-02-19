package org.group3.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.group3.exception.CompanyServiceException;
import org.group3.exception.ErrorType;
import org.group3.exception.HolidayServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {

    @Value("${holidayservice.secrets.secret-key}")
    String secretKey;
    @Value("${holidayservice.secrets.issuer}")
    String issuer;

    private final Long expirationTime=1000L*60*5;

    public Optional<String> createToken(Long id){
        String token=null;
        Date date=new Date(System.currentTimeMillis()+expirationTime);



        try{
            token= JWT.create()
                    .withClaim("id",id)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC512(secretKey));
        }catch (Exception e){
            System.out.println(e.getMessage());
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
            //String service = decodedJWT.getClaim("service").asString();
            //System.out.println("tokenin oluşturduğu service : "+service);
            return Optional.of(id);

        }catch (Exception e){
            return Optional.empty();
        }
    }

//    public Optional<String> createToken(Long id, ERole role){
//        String token=null;
//        Date date=new Date(System.currentTimeMillis()+expirationTime);
//
//        try{
//            token= JWT.create()
//                    .withClaim("id",id)
//                    .withClaim("role",role.toString())
//                    .withIssuer(issuer)
//                    .withIssuedAt(new Date())
//                    .withExpiresAt(date)
//                    .sign(Algorithm.HMAC512(secretKey));
//        }catch (Exception e){
//            throw new AdminManagerException(ErrorType.TOKEN_NOT_CREATED);
//        }
//        return Optional.ofNullable(token);
//
//    }

    public Optional<Long> getIdFromToken(String token){
        Algorithm algorithm=Algorithm.HMAC512(secretKey);
        JWTVerifier verifier= JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT decodedJWT= verifier.verify(token);
        if (decodedJWT==null){
            throw new HolidayServiceException(ErrorType.INVALID_TOKEN);
        }
        Long id=decodedJWT.getClaim("id").asLong();
        return Optional.of(id);
    }

    public Optional<String> getRoleFromToken(String token){
        Algorithm algorithm=Algorithm.HMAC512(secretKey);
        JWTVerifier verifier= JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT decodedJWT= verifier.verify(token);
        if (decodedJWT==null){
            throw new HolidayServiceException(ErrorType.INVALID_TOKEN);
        }
        String  role=decodedJWT.getClaim("role").asString();
        return Optional.of(role);
    }


}
