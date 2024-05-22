package com.sarop.saropbackend.restapi.dto.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoordinateResponse {

    private String id;
    private double x;
    private double y;
}
