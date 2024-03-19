package com.sarop.saropbackend.operation.dto;

import com.sarop.saropbackend.operationCategory.model.Category;
import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.user.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationUpdateRequest {

    @NotNull
    private int opNumber;

    @NotNull
    private Date operationDate;

    @NotEmpty
    private String name;


    @NotNull
    private Category category;


    @NotNull
    private List<User> responsiblePeople;

    @NotNull
    private Team operationalTeam;
}
