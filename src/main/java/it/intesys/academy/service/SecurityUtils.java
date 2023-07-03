package it.intesys.academy.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;

public class SecurityUtils {

    public static String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static List<String> getRoles() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities();

        return authorities.stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .toList();
    }
}
