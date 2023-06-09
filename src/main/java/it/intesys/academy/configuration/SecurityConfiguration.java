package it.intesys.academy.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                //.httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(authConverter())
                        )
                )
                .build();
    }

    private Converter<Jwt, AbstractAuthenticationToken> authConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var roles = jwt.getClaimAsStringList("roles");
            return roles
                    .stream()
                    .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                    .collect(Collectors.toList());
        });

        return jwtAuthenticationConverter;

    }


    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails enricoc =
                User.withUsername("ecostanzi")
                        .password("$2a$10$LjeELgJn3ADcXFTuaW.t7OaQIntcPFC8DmVHvT7hF5WvATj.79v.q") //bcrypt 'password' generated here https://www.browserling.com/tools/bcrypt
                        .roles("USER", "ADMIN")
                        .build();

        UserDetails enricoo =
                User.withUsername("eoliosi")
                        .password("$2a$10$5mghN9s6Is0RfzRbrolrcOxFBCkoWZ.YUlbg/uwFaqACxCvwJtQKG") //bcrypt 'password' generated here https://www.browserling.com/tools/bcrypt
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(enricoc, enricoo);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}
