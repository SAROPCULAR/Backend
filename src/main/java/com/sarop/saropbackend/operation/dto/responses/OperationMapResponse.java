package com.sarop.saropbackend.operation.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationMapResponse {
    private String id;
    private String mapName;
    private String workspaceName;

}
