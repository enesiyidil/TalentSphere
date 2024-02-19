package org.group3.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class ManagerSecurityConfig {

   @Bean
   public JwtTokenFilter getJwtTokenFilter(){
       return new JwtTokenFilter();
   }

    private final String[] WHITELIST={"/swagger-ui/**","/v3/api-docs/**","/manager/findAll","/manager/update"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

       httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests((authorizationManagerRequestMatcherRegistry -> {

            authorizationManagerRequestMatcherRegistry.requestMatchers(WHITELIST).permitAll()
                    .anyRequest().authenticated();

                }));

        //JwtTokenFilter ile kendi custom filterimizi yazdık
        httpSecurity.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
