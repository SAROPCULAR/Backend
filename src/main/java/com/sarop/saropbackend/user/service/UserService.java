package com.sarop.saropbackend.user.service;

import com.sarop.saropbackend.user.dto.responses.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
   // void changePassword(ChangePasswordRequest request, Principal connectedUser);

     List<UserResponse> findAllUser(Optional<String> email, Optional<String> id, Optional<String> name, Optional<String> teamName);



}
