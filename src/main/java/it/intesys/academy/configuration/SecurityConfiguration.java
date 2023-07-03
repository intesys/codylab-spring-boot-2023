package it.intesys.academy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                            .requestMatchers("/api/settings/**").hasAnyRole( "ADMIN")
                            .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                            .anyRequest().authenticated()
                    )
                    .httpBasic(Customizer.withDefaults())
                    .build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            UserDetails eoliosi =
                    User.withUsername("eoliosi")
                            .password("$2a$10$LjeELgJn3ADcXFTuaW.t7OaQIntcPFC8DmVHvT7hF5WvATj.79v.q")
                            .roles("USER", "ADMIN")
                            .build();

            UserDetails ecostanzi =
                    User.withUsername("ecostanzi")
                            .password("$2a$10$5mghN9s6Is0RfzRbrolrcOxFBCkoWZ.YUlbg/uwFaqACxCvwJtQKG")
                            .roles("USER")
                            .build();

            return new InMemoryUserDetailsManager(eoliosi, ecostanzi);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
}

