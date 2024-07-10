package com.teste.loja.config.security;

import com.teste.loja.config.passwordEncoder.PasswordEncoderConfiguration;
import com.teste.loja.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthenticationConfiguration configuration = new AuthenticationConfiguration();

    @Autowired
    private ApplicationContext springContainer;

    @Autowired
    private PasswordEncoderConfiguration configEncoder = new PasswordEncoderConfiguration();

    @Autowired
    private JWTUtil jwtUtil;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService, JWTUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((c) -> c.disable())
                .authorizeHttpRequests((aut) -> aut.requestMatchers(HttpMethod.POST, "/login").permitAll())
                .authorizeHttpRequests((auth) -> auth.anyRequest().authenticated())
                .addFilter(new JWTAuthenticationFilter(configuration.getAuthenticationManager(), jwtUtil))
                .addFilter(new JWTAuthorizationFilter(configuration.getAuthenticationManager(), jwtUtil, userDetailsService));
        return http.build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // tratar a autenticação dos usuários autenticar seus usuários.
        auth.inMemoryAuthentication()
                .withUser("usuario")
                .password(configEncoder.passwordEncoder().encode("senha"))
                .roles("ADMIN");
    }

//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    // Used by Spring Security if CORS is enabled.
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter((CorsConfigurationSource) source);
//    }
}
