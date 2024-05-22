package com.sarop.saropbackend.user.dto.responses;

import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;
    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;



    @NotNull

    private Role role;

    private UserTeamResponse team;


}
