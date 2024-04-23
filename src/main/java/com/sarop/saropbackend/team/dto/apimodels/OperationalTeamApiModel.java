package com.sarop.saropbackend.team.dto.apimodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationalTeamApiModel {
    @JsonProperty("OperationalTeamId")
    private String operationalTeamId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("FoundationYear")
    private int foundationYear;
    @JsonProperty("ProvinceCode")
    private String provinceCode;
    @JsonProperty("ProvinceName")
    private String provinceName;
    @JsonProperty("TeamLeaderName")
    private String teamLeaderName;
    @JsonProperty("TeamLeaderEMail")
    private String teamLeaderEMail;
    @JsonProperty("PhoneDescription")
    private String phoneDescription;
    @JsonProperty("IsVisibleWeb")
    private boolean isVisibleWeb;
    @JsonProperty("OperationalTeamLocations")
    private List<OperationalTeamLocation> operationalTeamLocations;

}
