package com.sarop.saropbackend.authentication.service.Abstract;

import com.sarop.saropbackend.authentication.dto.AuthenticationResponse;
import com.sarop.saropbackend.authentication.dto.LoginRequest;
import com.sarop.saropbackend.authentication.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
     AuthenticationResponse register(RegisterRequest request);
     AuthenticationResponse login(LoginRequest request) throws Exception;

     void refreshToken(
             HttpServletRequest request,
             HttpServletResponse response
     ) throws IOException;

     //AuthenticationResponse authenticateWithGoogle(OAuth2User principal);


}
