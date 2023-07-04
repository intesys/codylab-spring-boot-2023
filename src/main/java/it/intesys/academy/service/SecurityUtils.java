package it.intesys.academy.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;

public class SecurityUtils {
    public static String getUserName(){
        return ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("preferred_username");
    }

    public static List<String> getRoles(){
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities();
        return authorities.stream()
                .map(GrantedAuthority -> GrantedAuthority.getAuthority())
                .toList();
    }

}
