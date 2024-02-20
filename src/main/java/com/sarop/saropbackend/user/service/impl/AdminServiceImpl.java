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


    public User saveUser(UserSaveRequest userSaveRequest){
        var user = User.builder().firstName(userSaveRequest.getFirstName()).lastName(userSaveRequest.getLastName()).
                email(userSaveRequest.getEmail()).password(userSaveRequest.getPassword()).role(userSaveRequest.getRole()).build();
        return userRepository.save(user);
    }

    public User updateUser(String id, UserUpdateRequest userUpdateRequest){
        User user = userRepository.findById(id).orElseThrow();
        if(userUpdateRequest.getFirstName() != null){
            user.setFirstName(userUpdateRequest.getFirstName());
        }
        if(userUpdateRequest.getLastName() != null){
            user.setLastName(userUpdateRequest.getLastName());
        }
        if(userUpdateRequest.getEmail() != null){
            user.setEmail(userUpdateRequest.getEmail());
        }
        if(userUpdateRequest.getPassword() != null){
            user.setEmail(userUpdateRequest.getPassword());
        }
        if(userUpdateRequest.getPassword() != null){
            user.setPassword(userUpdateRequest.getPassword());
        }
        return userRepository.save(user);
    }

    public void deleteUser(String email){
        User user = userRepository.findByEmail(email).orElseThrow();
        userRepository.delete(user);
    }






}
