package com.sarop.saropbackend.authentication.mapper;

import com.sarop.saropbackend.authentication.dto.RegisterRequest;
import com.sarop.saropbackend.user.model.Role;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.model.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationMapper {

    private final PasswordEncoder passwordEncoder;

    public User registerRequestToUser(RegisterRequest registerRequest){
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER).userStatus(UserStatus.NOT_VERIFIED).build();
        return user;
    }
}
