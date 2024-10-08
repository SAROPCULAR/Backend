package com.sarop.saropbackend.user.service.impl;


import com.sarop.saropbackend.exception.UserNotFoundException;
import com.sarop.saropbackend.note.model.Note;
import com.sarop.saropbackend.note.repository.NoteRepository;
import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.team.repository.TeamRepository;
import com.sarop.saropbackend.user.dto.UserSaveRequest;
import com.sarop.saropbackend.user.dto.UserUpdateRequest;
import com.sarop.saropbackend.user.dto.responses.UserResponse;
import com.sarop.saropbackend.user.dto.responses.UserTeamResponse;
import com.sarop.saropbackend.user.model.Role;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.model.UserStatus;
import com.sarop.saropbackend.user.repository.UserRepository;
import com.sarop.saropbackend.user.service.AdminService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TeamRepository teamRepository;

    private final NoteRepository noteRepository;

    @PostConstruct
    public void initializeAdminUser() {
        if(!userRepository.existsByRole(Role.ADMIN)){
            var admin = User.builder().name("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN).status(UserStatus.VERIFIED).build();
            userRepository.save(admin);
        }
    }


    public User saveUser(UserSaveRequest userSaveRequest) {

        if(!teamRepository.findById(userSaveRequest.getTeamId()).isPresent()){
            var user = User.builder().name(userSaveRequest.getName()).
                    email(userSaveRequest.getEmail()).
                    password(passwordEncoder.encode(userSaveRequest.getPassword())).
                    role(userSaveRequest.getRole()).status(UserStatus.VERIFIED).build();
            userRepository.save(user);
            return user;

        }else{
            Team team = teamRepository.findById(userSaveRequest.getTeamId()).orElseThrow();
            var user = User.builder().name(userSaveRequest.getName()).
                    email(userSaveRequest.getEmail()).
                    password(passwordEncoder.encode(userSaveRequest.getPassword())).
                    role(userSaveRequest.getRole()).status(UserStatus.VERIFIED).team(team).build();
            team.getMembers().add(user);
            userRepository.save(user);
            teamRepository.save(team);
            return user;
        }


    }

    @Transactional
    public User updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException());
        user.setName(userUpdateRequest.getName());
        if(user.getEmail() != userUpdateRequest.getEmail()){
            user.setEmail(userUpdateRequest.getEmail());
        }
        if(!userUpdateRequest.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        }
        if(teamRepository.findById(userUpdateRequest.getTeamId()).isPresent()){
            Team team = teamRepository.findById(userUpdateRequest.getTeamId()).orElseThrow();
            if(team.getTeamLeader() != null){
                if(team.getTeamLeader().equals(user)){
                    team.setTeamLeader(null);
                    user.setRole(Role.USER);
                }
            }

            user.setTeam(team);
        }
        user.setRole(userUpdateRequest.getRole());

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        for (Note note : user.getNotes()) {
            note.getMap().getNotes().remove(note);
            noteRepository.delete(note);
        }

        if (user.getTeam() != null) {
            if(user.getTeam().getTeamLeader() != null){
                if(user.getTeam().getTeamLeader().equals(user)){
                    user.getTeam().setTeamLeader(null);
                }
            }

            user.getTeam().getMembers().remove(user);
        }


        userRepository.delete(user);
    }

    @Transactional
    public void verifyUser(String id){
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException());
        user.setStatus(UserStatus.VERIFIED);
        userRepository.save(user);
    }

    public List<UserResponse> findAllNotVerifiedUsers(Optional<String> email, Optional<String> id, Optional<String> name, Optional<String> teamName){
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findAll().stream()
                .filter(user ->
                        ((!email.isPresent()|| user.getEmail().equals(email)) ||
                                (!id.isPresent() || user.getId().equals(id)) ||
                                (!name.isPresent() || (user.getName()).equals(name))
                                || (!teamName.isPresent() || (user.getTeam().getName()).equals(teamName)))
                                && (user.getStatus() == UserStatus.NOT_VERIFIED)
                )
                .collect(Collectors.toList());
        for(User user : users){
            var userResponse = UserResponse.builder().id(user.getId()).name(user.getName())
                    .role(user.getRole()).email(user.getEmail()).build();
            if(user.getTeam() != null){
                var userTeamResponse = UserTeamResponse.builder().id(user.getTeam().getId()).name(user.getTeam().getName()).build();
                userResponse.setTeam(userTeamResponse);
            }

            userResponses.add(userResponse);
        }
        return userResponses;
    }

}
