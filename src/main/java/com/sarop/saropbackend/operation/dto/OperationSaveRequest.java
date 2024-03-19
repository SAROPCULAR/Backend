package com.sarop.saropbackend.operation.dto;


import com.sarop.saropbackend.team.model.Team;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationSaveRequest {



    @NotNull
    private int opNumber;

    @NotNull
    private Date operationDate;

    @NotEmpty
    private String name;

    @NotNull
    private Team operationalTeam;


}
