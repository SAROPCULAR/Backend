package com.sarop.saropbackend.restapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CoverageStoreRequest {
    private String coverageStoreName;
    private String coverageStoreUrl;


}
