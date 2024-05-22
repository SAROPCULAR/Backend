package com.sarop.saropbackend.note.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteMapResponse {
    private String id;
    private String mapName;
    private String workspaceName;
}
