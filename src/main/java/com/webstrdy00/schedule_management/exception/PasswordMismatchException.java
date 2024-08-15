package com.webstrdy00.schedule_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class PasswordMismatchException extends RuntimeException{

    public PasswordMismatchException(String message){
        super(message);
    }

    public PasswordMismatchException(String message, Throwable cause){
        super(message, cause);
    }
}
