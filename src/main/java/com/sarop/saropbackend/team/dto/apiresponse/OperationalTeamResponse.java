package com.sarop.saropbackend.team.dto.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sarop.saropbackend.team.dto.apimodels.OperationalTeamApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationalTeamResponse {

    @JsonProperty("StartDateTime")
    private String  startDateTime;
    @JsonProperty("OperationalTeamApiModels")
    private List<OperationalTeamApiModel> operationalTeamApiModels;
}
