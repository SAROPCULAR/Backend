package com.sarop.saropbackend.note.service;

import com.sarop.saropbackend.note.entity.Note;
import com.sarop.saropbackend.note.repository.NoteRepository;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.restapi.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService{
    private final NoteRepository noteRepository;


    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;

    }

    public Note createNoteWithMap(Note note, String mapId) {

        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }



}
