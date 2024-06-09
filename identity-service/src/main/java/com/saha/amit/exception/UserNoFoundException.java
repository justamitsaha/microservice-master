package com.saha.amit.exception;

public class UserNoFoundException extends RuntimeException{
    public String message;

    public UserNoFoundException(String message){
        super(message);
        this.message = message;
    }
}