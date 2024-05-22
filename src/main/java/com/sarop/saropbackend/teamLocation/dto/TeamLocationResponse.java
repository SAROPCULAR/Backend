package com.sarop.saropbackend.teamLocation.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamLocationResponse {


    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String provinceCode;

    @NotEmpty
    private String provinceName;

    @NotEmpty

    private String countyName;

    @NotEmpty

    private String address;

    @NotEmpty
    private double latitude;

    @NotEmpty
    private double longitude;


    private String description;

    @Length(max = 11)
    private String phoneNumber;

    @Length(max = 11)
    private String secondPhoneNumber;

    @Length(max = 11)
    private String thirdPhoneNumber;

    @Length(max = 11)
    private String faxNumber;

    private TeamLocationTeamResponse team;
}
