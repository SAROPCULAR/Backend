package com.sarop.saropbackend.exception;

public class UserNotFoundException extends NotFoundException{

    public UserNotFoundException() {
        super("User does not found!");
    }
}
