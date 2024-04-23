package com.sarop.saropbackend.authentication.service.Concrete;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarop.saropbackend.authentication.dto.AuthenticationResponse;

import com.sarop.saropbackend.authentication.dto.LoginRequest;
import com.sarop.saropbackend.authentication.dto.RegisterRequest;
import com.sarop.saropbackend.authentication.service.Abstract.AuthenticationService;
import com.sarop.saropbackend.config.JWTService;
import com.sarop.saropbackend.exception.UserNotFoundException;
import com.sarop.saropbackend.user.model.Role;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.model.UserStatus;
import com.sarop.saropbackend.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${application.security.jwt.header.string}")
    public String HEADER_STRING;

    @Value("${application.security.jwt.token.prefix}")
    public String TOKEN_PREFIX;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;




    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder().name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).status(UserStatus.NOT_VERIFIED).build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Transactional
    public AuthenticationResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException());
        if(user.getStatus() == UserStatus.VERIFIED){
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }else{
            throw new Error("User is not verified");
        }
    }





    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HEADER_STRING);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith(TOKEN_PREFIX)) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow(()-> new UserNotFoundException());
            if (jwtService.isTokenValid(refreshToken, userEmail)) {
                var accessToken = jwtService.generateToken(user);
             //   revokeAllUserTokens(user);
             //   saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
    /*
    @Override
    public AuthenticationResponse authenticateWithGoogle(OAuth2User principal) {

            String email = principal.getAttribute("email");
            User user = userRepository.findByEmail(email).orElseThrow();
            if(user == null){
               return registerWithGoogle(email,principal);
            }else{
                return loginWithGoogle(user);
            }

    }

    private AuthenticationResponse registerWithGoogle(String email,OAuth2User user){
        User newUser = User.builder()
                .email(email)
                .firstName(user.getName())
                .role(Role.USER)
                .build();

        var savedUser = userRepository.save(newUser);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private AuthenticationResponse loginWithGoogle(User user){
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    */

}
