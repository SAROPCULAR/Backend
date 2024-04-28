package com.sarop.saropbackend.polygon.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolygonAddRequest {

    @NotNull
    private List<List<Double>> coordinates;

    @NotNull
    private String mapId;
}
