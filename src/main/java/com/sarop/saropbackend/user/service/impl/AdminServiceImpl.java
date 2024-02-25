package com.sarop.saropbackend.user.service.impl;


import com.sarop.saropbackend.user.dto.UserSaveRequest;
import com.sarop.saropbackend.user.dto.UserUpdateRequest;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.repository.UserRepository;
import com.sarop.saropbackend.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;


    public User saveUser(UserSaveRequest userSaveRequest) {
        var user = User.builder().firstName(userSaveRequest.getFirstName()).lastName(userSaveRequest.getLastName()).
                email(userSaveRequest.getEmail()).password(userSaveRequest.getPassword()).role(userSaveRequest.getRole()).build();
        return userRepository.save(user);
    }

    public User updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(userUpdateRequest.getPassword());

        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }


}
