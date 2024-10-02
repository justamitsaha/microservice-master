package com.saha.amit.controller;


import com.saha.amit.exception.CustomerNotFindException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ProductControllerAdvice {

    Log log = LogFactory.getLog(ProductController.class);

    @ExceptionHandler(CustomerNotFindException.class)
    public ProblemDetail handleException(CustomerNotFindException customerNotFindException){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,customerNotFindException.getMessage());
        problemDetail.setType(URI.create("https://www.youtube.com/"));
        problemDetail.setTitle("Customer not find");
        return problemDetail;
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleException(RuntimeException runtimeException){
        log.error(runtimeException);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,runtimeException.getMessage());
        problemDetail.setType(URI.create("https://www.youtube.com/"));
        problemDetail.setTitle(runtimeException.getMessage());
        return problemDetail;
    }
}
