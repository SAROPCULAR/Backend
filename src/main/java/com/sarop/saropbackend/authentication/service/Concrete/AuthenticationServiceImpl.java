package com.sarop.saropbackend.authentication.service.Concrete;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarop.saropbackend.authentication.dto.AuthenticationResponse;

import com.sarop.saropbackend.authentication.dto.LoginRequest;
import com.sarop.saropbackend.authentication.dto.RegisterRequest;
import com.sarop.saropbackend.authentication.mapper.AuthenticationMapper;
import com.sarop.saropbackend.authentication.service.Abstract.AuthenticationService;
import com.sarop.saropbackend.config.JWTService;
import com.sarop.saropbackend.user.model.UserStatus;
import com.sarop.saropbackend.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    private final AuthenticationMapper authenticationMapper;


    public AuthenticationResponse register(RegisterRequest request) {
        var user = authenticationMapper.registerRequestToUser(request);
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }



    public AuthenticationResponse login(LoginRequest request) throws Exception {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new Exception("Wrong email or password");
        }
        if(user.getUserStatus() == UserStatus.NOT_VERIFIED) {
            throw new Exception("User is not verified");
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
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
                    .orElseThrow();
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




}
