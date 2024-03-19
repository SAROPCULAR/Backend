package com.sarop.saropbackend.user.mapper;

import com.sarop.saropbackend.user.dto.UserSaveRequest;
import com.sarop.saropbackend.user.dto.UserUpdateRequest;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.model.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    public User userSaveRequestToUser(UserSaveRequest userSaveRequest){
        var user = User.builder().firstName(userSaveRequest.getFirstName()).lastName(userSaveRequest.getLastName()).
                email(userSaveRequest.getEmail()).password(passwordEncoder.encode(userSaveRequest.getPassword()))
                .role(userSaveRequest.getRole()).team(userSaveRequest.getTeam())
                .userStatus(UserStatus.VERIFIED).build();
        return user;
    }

    public User userUpdateRequestToUser(User user,UserUpdateRequest userUpdateRequest){
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        user.setRole(userUpdateRequest.getRole());
        user.setTeam(userUpdateRequest.getTeam());
        return user;
    }
}
