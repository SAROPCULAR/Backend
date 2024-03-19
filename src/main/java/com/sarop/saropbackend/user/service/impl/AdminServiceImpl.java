package com.sarop.saropbackend.user.service.impl;


import com.sarop.saropbackend.user.dto.UserSaveRequest;
import com.sarop.saropbackend.user.dto.UserUpdateRequest;
import com.sarop.saropbackend.user.mapper.UserMapper;
import com.sarop.saropbackend.user.model.Role;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.model.UserStatus;
import com.sarop.saropbackend.user.repository.UserRepository;
import com.sarop.saropbackend.user.service.AdminService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    @PostConstruct
    public void initializeAdminUser() {
        if(!userRepository.existsByRole(Role.ADMIN)){
            var admin = User.builder().firstName("admin")
                    .lastName("User").email("admin@example.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN).userStatus(UserStatus.VERIFIED).build();
            userRepository.save(admin);
        }
    }

    public User saveUser(UserSaveRequest userSaveRequest) {
        var user = userMapper.userSaveRequestToUser(userSaveRequest);
        return userRepository.save(user);
    }

    public User updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow();
        user = userMapper.userUpdateRequestToUser(user,userUpdateRequest);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

    public void verifyUser(String id){
        User user = userRepository.findById(id).orElseThrow();
        user.setUserStatus(UserStatus.VERIFIED);
        userRepository.save(user);
    }


}
