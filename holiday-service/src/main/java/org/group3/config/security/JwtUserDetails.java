package org.group3.config.security;


import lombok.RequiredArgsConstructor;

import org.group3.dto.response.HolidayResponseDto;

import org.group3.service.HolidayService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetailsService {

    private final HolidayService holidayService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByAuthid(Long id, String role){
        HolidayResponseDto holiday = holidayService.findById(id);
        if (holiday==null){
            return null;
        }
        List<GrantedAuthority> authorities=new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role));


        return User.builder()
                .username(holiday.getName())
                .password("")
                .accountExpired(false)
                .accountLocked(false)
                .authorities(authorities)
                .build();
    }
}
