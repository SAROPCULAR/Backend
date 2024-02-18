package com.sarop.saropbackend.authentication.controller;


import com.sarop.saropbackend.authentication.dto.AuthenticationResponse;
import com.sarop.saropbackend.authentication.dto.LoginRequest;
import com.sarop.saropbackend.authentication.dto.RegisterRequest;
import com.sarop.saropbackend.authentication.service.Concrete.AuthenticationService;
import com.sarop.saropbackend.authentication.service.Concrete.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationService authenticationService;

    private final LogoutService logoutService;



    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){

        return ResponseEntity.ok(authenticationService.register(request));
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }


    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                                    HttpServletResponse response, Authentication authentication){
        logoutService.logout(request, response, authentication);
    }
}
