package com.sarop.saropbackend.exception;

public class UserNotVerifiedException extends SaropException{

    public UserNotVerifiedException(){
        super("User is not verified");
    }
}
