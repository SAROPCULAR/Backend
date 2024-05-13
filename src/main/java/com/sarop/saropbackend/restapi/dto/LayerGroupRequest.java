package com.sarop.saropbackend.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LayerGroupRequest {
    private String name;
    private Mode mode;
    private String title;
    private String abstractText;
    private String workspace;
    private List<String> layers;

}
