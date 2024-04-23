package com.sarop.saropbackend.exception;

public class UserNotFoundException extends SaropException{

    public UserNotFoundException() {
        super("User does not found!");
    }
}
