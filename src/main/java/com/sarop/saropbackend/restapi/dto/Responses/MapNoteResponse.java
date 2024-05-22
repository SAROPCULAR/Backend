package com.sarop.saropbackend.restapi.dto.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapNoteResponse {

    private String id;
    private MapNoteUserResponse user;

    private String comment;

    private CoordinateResponse coordinate;
}
