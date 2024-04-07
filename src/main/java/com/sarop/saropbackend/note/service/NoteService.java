package com.sarop.saropbackend.note.service;

import com.sarop.saropbackend.note.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {


    List<Note> getAllNotes();

    void deleteNoteById(String id);

    Note createNoteWithMap(Note note, String mapId);
}
