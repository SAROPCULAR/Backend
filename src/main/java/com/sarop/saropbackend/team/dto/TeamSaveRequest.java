package com.sarop.saropbackend.team.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamSaveRequest {

    @NotEmpty
    private String name;

    @NotNull
    private int foundationYear;

    @NotEmpty
    private String provinceCode;

    @NotEmpty
    private String provinceName;

    @NotEmpty
    @Email
    private String teamLeaderEmail;


    @NotEmpty
    @Length(max = 11)
    private String phoneDescription;


    @NotEmpty
    private List<String> teamLocations;

    @NotEmpty
    private List<String> users;
}
