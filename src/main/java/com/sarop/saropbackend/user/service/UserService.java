package com.sarop.saropbackend.user.service;

import com.sarop.saropbackend.user.dto.ChangePasswordRequest;
import com.sarop.saropbackend.user.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {
   // void changePassword(ChangePasswordRequest request, Principal connectedUser);

     List<User> findAllUser(Optional<String> email, Optional<String> id, Optional<String> name, Optional<String> teamName);



}
