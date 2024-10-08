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


    private String name;


    private int foundationYear;


    private String provinceCode;


    private String provinceName;


    @Email
    private String teamLeaderEmail;



    @Length(max = 11)
    private String phoneDescription;



    private List<String> teamLocations;


    private List<String> users;

}
