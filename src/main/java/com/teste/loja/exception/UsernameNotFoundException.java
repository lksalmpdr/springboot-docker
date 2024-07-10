package com.teste.loja.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UsernameNotFoundException extends Exception{
    public UsernameNotFoundException(String msg){
        super(msg);
    }
}
