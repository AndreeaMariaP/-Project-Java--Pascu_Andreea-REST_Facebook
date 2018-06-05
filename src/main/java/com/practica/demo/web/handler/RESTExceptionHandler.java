package com.practica.demo.web.handler;

import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueAlreadyInUseException;
import com.practica.demo.model.exception.ValueNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class RESTExceptionHandler {
	
	private final Logger LOG = LoggerFactory.getLogger(RESTExceptionHandler.class);

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    String handleBadRequestException(HttpServletResponse response, AuthorizationException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidArgumentsException.class)
    @ResponseBody
    String handleInvalidArgumentsException(HttpServletResponse response, InvalidArgumentsException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ValueAlreadyInUseException.class)
    @ResponseBody
    String handleBadArgumentsException(HttpServletResponse response, ValueAlreadyInUseException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ValueNotFoundException.class)
    @ResponseBody
    String handleValueNotFoundException(HttpServletResponse response, ValueNotFoundException ex) {
        return ex.getMessage();
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    String handleAnyUncaughtException(HttpServletResponse response, Exception ex) {
//        return "An unexpected error has occured";
//    }


}
