package com.sarop.saropbackend.user.service.impl;

import com.sarop.saropbackend.user.dto.ChangePasswordRequest;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.model.UserStatus;
import com.sarop.saropbackend.user.repository.UserRepository;
import com.sarop.saropbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    /*
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

     */

    public List<User> findAllUser(Optional<String> email, Optional<String> id, Optional<String> name, Optional<String> teamName) {
        List<User> users = repository.findAll().stream()
                .filter(user ->
                        (email.isEmpty() || user.getEmail().equals(email.get())) &&
                                (id.isEmpty() || user.getId().equals(id.get())) &&
                                (name.isEmpty() || user.getName().equals(name.get())) &&
                                (teamName.isEmpty() || user.getTeam().getName().equals(teamName.get())) &&
                                user.getStatus() == UserStatus.VERIFIED
                )
                .collect(Collectors.toList());
        return users;
    }


}
