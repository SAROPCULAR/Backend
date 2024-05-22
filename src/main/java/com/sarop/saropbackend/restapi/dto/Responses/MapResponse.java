package com.sarop.saropbackend.restapi.dto.Responses;

import com.sarop.saropbackend.restapi.entity.Workspace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapResponse {

    private String id;
    private String mapName;

    private String mapType;

    private String mapDescription;

    private String displayUrl;

    private MapWorkspaceResponse workspace;

    private List<MapOperationResponse> operations;

    private List<MapNoteResponse> notes;

    private List<MapPolygonResponse> polygons;
}
