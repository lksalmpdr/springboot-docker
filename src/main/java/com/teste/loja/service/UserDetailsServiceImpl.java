package com.teste.loja.service;

import com.teste.loja.config.passwordEncoder.PasswordEncoderConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

    PasswordEncoderConfiguration confiEncoder = new PasswordEncoderConfiguration();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // buscar usuário em banco
        if ("usuario".equals(username)) {
            return new User("usuario", confiEncoder.passwordEncoder().encode("senha"), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}
