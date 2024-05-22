package com.sarop.saropbackend.polygon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolygonMapResponse {
    private String id;
    private String mapName;
    private String workspaceName;
}
