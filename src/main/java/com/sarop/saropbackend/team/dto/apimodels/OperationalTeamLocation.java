package com.sarop.saropbackend.team.dto.apimodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationalTeamLocation {
    @JsonProperty("OperationalTeamLocationId")
    private String operationalTeamLocationId;
    @JsonProperty("OperationalTeamId")
    private String operationalTeamId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("ProvinceCode")
    private String provinceCode;
    @JsonProperty("ProvinceName")
    private String provinceName;
    @JsonProperty("CountyName")
    private String countyName;
    @JsonProperty("Address")
    private String address;
    @JsonProperty("Latitude")
    private double latitude;
    @JsonProperty("Longitude")
    private double longitude;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("FirstPhoneNumber")
    private String firstPhoneNumber;
    @JsonProperty("SecondPhoneNumber")
    private String secondPhoneNumber;
    @JsonProperty("ThirdPhoneNumber")
    private String thirdPhoneNumber;
    @JsonProperty("FaxNumber")
    private String faxNumber;
    @JsonProperty("IsVisibleWeb")
    private boolean isVisibleWeb;
}
