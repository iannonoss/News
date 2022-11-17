package com.example.news.exception;

public class MismatchedIdException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public MismatchedIdException(String message){
        super(message);
    }



}
