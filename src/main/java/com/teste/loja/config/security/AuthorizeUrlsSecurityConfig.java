package com.teste.loja.config.security;

//@Configuration
public class AuthorizeUrlsSecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests((auth) -> auth
//                        .anyRequest().authenticated())
//                .securityMatcher("/swagger-resources/*", "*.html", "/api/v1/swagger.json")
//                .addFilterBefore(new BasicAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//                .requestMatchers("/admin")
//                .hasRole("ADMIN")
//                .requestMatchers("/**")
//                .hasRole("USER").and().formLogin();

//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
