package com.sarop.saropbackend.user.service;

import com.sarop.saropbackend.user.dto.ChangePasswordRequest;
import com.sarop.saropbackend.user.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser);

     List<User> findAllUser();



}
