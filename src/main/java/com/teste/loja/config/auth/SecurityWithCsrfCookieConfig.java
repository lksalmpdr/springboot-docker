package com.teste.loja.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityWithCsrfCookieConfig {
    //@Bean
    //public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.csrfTokenRepository
//                (CookieCsrfTokenRepository.withHttpOnlyFalse()));
        //http.csrf(AbstractHttpConfigurer::disable);
        //return http.build();
    //}
}