package com.sarop.saropbackend.restapi.dto.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceResponse {
    private String id;
    private String name;
    private List<WorkspaceMapResponse> maps;
}
