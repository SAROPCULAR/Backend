package com.sarop.saropbackend.authentication.service.Abstract;

import com.sarop.saropbackend.authentication.dto.AuthenticationResponse;
import com.sarop.saropbackend.authentication.dto.LoginRequest;
import com.sarop.saropbackend.authentication.dto.RegisterRequest;

public interface AuthenticationService {
     AuthenticationResponse register(RegisterRequest request);
     AuthenticationResponse login(LoginRequest request) ;
}
