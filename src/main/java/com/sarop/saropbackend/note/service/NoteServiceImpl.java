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
    private final MapRepository mapRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository,MapRepository mapRepository) {
        this.noteRepository = noteRepository;
        this.mapRepository = mapRepository;
    }

    public Note createNoteWithMap(Note note, String mapId) {
        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new IllegalArgumentException("Map not found with id: " + mapId));
        note.setMap(map);
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }



}
