package com.sarop.saropbackend.user.service.impl;


import com.sarop.saropbackend.exception.UserNotFoundException;
import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.team.repository.TeamRepository;
import com.sarop.saropbackend.user.dto.UserSaveRequest;
import com.sarop.saropbackend.user.dto.UserUpdateRequest;
import com.sarop.saropbackend.user.model.Role;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.model.UserStatus;
import com.sarop.saropbackend.user.repository.UserRepository;
import com.sarop.saropbackend.user.service.AdminService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TeamRepository teamRepository;

    @PostConstruct
    public void initializeAdminUser() {
        if(!userRepository.existsByRole(Role.ADMIN)){
            var admin = User.builder().firstName("admin")
                    .lastName("User").email("admin@example.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN).status(UserStatus.VERIFIED).build();
            userRepository.save(admin);
        }
    }
    public User saveUser(UserSaveRequest userSaveRequest) {
        Team team = teamRepository.findTeamByName(userSaveRequest.getTeamName());
        var user = User.builder().firstName(userSaveRequest.getFirstName()).lastName(userSaveRequest.getLastName()).
                email(userSaveRequest.getEmail()).
                password(passwordEncoder.encode(userSaveRequest.getPassword())).
                role(userSaveRequest.getRole()).status(UserStatus.VERIFIED).team(team).build();
        return userRepository.save(user);
    }

    public User updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException());
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        Team team = teamRepository.findTeamByName(userUpdateRequest.getTeamName());
        user.setTeam(team);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException());
        userRepository.delete(user);
    }

    public void verifyUser(String id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException());
        user.setStatus(UserStatus.VERIFIED);
        userRepository.save(user);
    }

    public List<User> findAllNotVerifiedUsers(){
        return userRepository.findAllByStatus(UserStatus.NOT_VERIFIED);
    }

}
