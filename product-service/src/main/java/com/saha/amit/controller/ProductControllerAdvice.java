package com.saha.amit.controller;


import com.saha.amit.exception.CustomerNotFindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(CustomerNotFindException.class)
    public ProblemDetail handleException(CustomerNotFindException customerNotFindException){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,customerNotFindException.getMessage());
        problemDetail.setType(URI.create("https://www.youtube.com/"));
        problemDetail.setTitle("Customer not find");
        return problemDetail;
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleException(RuntimeException runtimeException){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,runtimeException.getMessage());
        problemDetail.setType(URI.create("https://www.youtube.com/"));
        problemDetail.setTitle("Customer not find");
        return problemDetail;
    }
}
