package com.sarop.saropbackend.user.dto;

import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class UserUpdateRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Role role;

    @NotNull
    private Team team;
}
