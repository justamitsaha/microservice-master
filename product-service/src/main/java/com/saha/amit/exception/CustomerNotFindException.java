package com.saha.amit.exception;

public class CustomerNotFindException  extends RuntimeException{

    public static final String MESSAGE = "Customer with [id=%d] not found";

   public CustomerNotFindException(Integer id){
        super(MESSAGE.formatted(id));
    }
}
