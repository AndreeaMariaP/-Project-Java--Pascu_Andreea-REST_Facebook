package com.practica.demo.model.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason = "You are not authorized for this action")
public class AuthorizationException extends Exception {
    public AuthorizationException(String message) {
        super(message);
    }
}
