package com.sarop.saropbackend.note.dto;

import com.sarop.saropbackend.restapi.dto.Responses.CoordinateResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {
    private String id;
    private NoteUserResponse user;
    private String comment;

    private NoteMapResponse map;

    private CoordinateResponse coordinate;


}
