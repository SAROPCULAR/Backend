package com.sarop.saropbackend.exception;

public abstract class NotFoundException extends RuntimeException {

    protected NotFoundException(String message){
        super(message);
    }
}
