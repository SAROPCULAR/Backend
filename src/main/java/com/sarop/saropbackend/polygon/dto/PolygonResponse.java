package com.sarop.saropbackend.polygon.dto;


import com.sarop.saropbackend.restapi.dto.Responses.CoordinateResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolygonResponse {
    private String id;
    private List<CoordinateResponse> coordinates;
    private PolygonMapResponse map;

}
