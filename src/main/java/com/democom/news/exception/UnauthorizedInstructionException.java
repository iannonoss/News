package com.democom.news.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedInstructionException extends RuntimeException{
    public UnauthorizedInstructionException(String message){
        super(message);
    }
}
