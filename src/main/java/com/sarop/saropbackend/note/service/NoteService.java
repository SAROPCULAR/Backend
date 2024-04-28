package com.sarop.saropbackend.note.service;

import com.sarop.saropbackend.note.dto.NoteAddRequest;
import com.sarop.saropbackend.note.model.Note;

import java.util.List;

public interface NoteService {

    List<Note> getAllNotesByMapId(String mapId);
    Note addNote(NoteAddRequest noteAddRequest);

    void deleteNote(String id);

}
