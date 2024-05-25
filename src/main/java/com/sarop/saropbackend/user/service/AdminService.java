package com.sarop.saropbackend.user.service;

import com.sarop.saropbackend.user.dto.UserSaveRequest;
import com.sarop.saropbackend.user.dto.UserUpdateRequest;
import com.sarop.saropbackend.user.dto.responses.UserResponse;
import com.sarop.saropbackend.user.model.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    User saveUser(UserSaveRequest userSaveRequest);
    User updateUser(String id, UserUpdateRequest userUpdateRequest);

    void deleteUser(String id);

    void verifyUser(String id);
    List<UserResponse> findAllNotVerifiedUsers(Optional<String> email, Optional<String> id, Optional<String> name, Optional<String> teamName);
}
