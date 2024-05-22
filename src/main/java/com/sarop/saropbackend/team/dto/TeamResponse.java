package com.sarop.saropbackend.team.dto;


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
public class TeamResponse {

    private String id;

    private String name;


    private int foundationYear;


    private String provinceCode;


    private String provinceName;



    private TeamUserResponse teamLeader;



    @Length(max = 11)
    private String phoneDescription;



    private List<TeamTeamLocationResponse> teamLocations;

    private List<TeamUserResponse> users;
}
