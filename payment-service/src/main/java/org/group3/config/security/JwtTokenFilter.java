package org.group3.config.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.group3.exception.ErrorType;
import org.group3.exception.HolidayServiceException;
import org.group3.exception.PaymentServiceException;
import org.group3.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenManager jwtTokenManager;

    @Autowired
    JwtUserDetails jwtUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("!!! JwtTokenFilter devrede !!!");

        //istkte gelen bearer tokeni yakalamak
        String bearerToken=request.getHeader("Authorization");
        if (Objects.nonNull(bearerToken)&&bearerToken.startsWith("Bearer ")){
            String token=bearerToken.substring(7);
            //tokenden auth id alma
            Optional<Long> authid = jwtTokenManager.decodeToken(token);
            Optional<String> role= jwtTokenManager.getRoleFromToken(token);

            //authid boş ise token yanlıştır
            if (authid.isEmpty() || (role.isEmpty())){
                throw new PaymentServiceException(ErrorType.INVALID_TOKEN);
            }


            UserDetails userDetails=jwtUserDetails.loadUserByAuthid(authid.get(), role.get());
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

            //tokeni springe aktarma kısmı
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request,response);
    }
}
