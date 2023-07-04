package it.intesys.academy.service;

import com.nimbusds.jwt.JWT;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityUtils {

/*    public static String getCurrentUser() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }*/
    public static String getCurrentUser(){
        return ((Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("preferred_username");
    }
    public static boolean hasAuthority(String authority) {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals(authority));
    }
}
