package com.quickcart.userservice.exception;

public class ResourceAlreadyExistsException extends RuntimeException{
    public ResourceAlreadyExistsException(String msg){
        super(msg);
    }
}
