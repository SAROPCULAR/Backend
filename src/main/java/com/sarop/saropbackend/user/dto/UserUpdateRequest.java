package com.sarop.saropbackend.user.dto;

import com.sarop.saropbackend.user.model.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String password;

    private Role role;
}
