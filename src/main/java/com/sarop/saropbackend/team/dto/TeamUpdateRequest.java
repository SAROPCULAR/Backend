package com.sarop.saropbackend.team.dto;

import com.sarop.saropbackend.locations.model.OperationalTeamLocations;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.user.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamUpdateRequest {

    @NotEmpty
    private String name;


    @NotEmpty
    private String description;

    @NotNull
    private int foundationYear;

    @NotEmpty
    private String provinceCode;

    @NotEmpty
    private String provinceName;

    @NotNull
    private User teamLeader;

    @NotNull
    private List<User> members;

    @NotNull
    private List<OperationalTeamLocations> operationalTeamLocations;

    @NotNull
    private List<Operation> operations;
}
