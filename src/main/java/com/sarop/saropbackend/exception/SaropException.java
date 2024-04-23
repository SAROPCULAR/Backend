package com.sarop.saropbackend.exception;

public abstract class SaropException extends RuntimeException {

    protected SaropException(String message){
        super(message);
    }
}
