package com.sarop.saropbackend.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCoverageStoreRequest {
    private String layerName;
    private String fileUrl;
}
