package com.sarop.saropbackend.note.service;

import com.sarop.saropbackend.note.dto.NoteAddRequest;
import com.sarop.saropbackend.note.dto.NoteResponse;
import com.sarop.saropbackend.note.dto.NoteUserResponse;
import com.sarop.saropbackend.note.model.Note;

import java.util.List;

public interface NoteService {

    List<NoteResponse> getAllNotesByMapId(String mapId);
    Note addNote(NoteAddRequest noteAddRequest);

    void deleteNote(String id);

}
