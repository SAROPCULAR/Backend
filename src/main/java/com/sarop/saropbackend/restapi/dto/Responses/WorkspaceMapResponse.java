package com.sarop.saropbackend.restapi.dto.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceMapResponse {

    private String id;
    private String mapName;

    private String mapType;

    private String mapDescription;

    private String displayUrl;
}
