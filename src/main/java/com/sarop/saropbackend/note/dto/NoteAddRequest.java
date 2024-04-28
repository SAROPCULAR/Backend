package com.sarop.saropbackend.note.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteAddRequest {

    @NotEmpty
    private String userEmail;

    @NotEmpty
    private String comment;

    @NotNull
    private double x;

    @NotNull
    private double y;

    @NotEmpty
    private String mapId;
}
