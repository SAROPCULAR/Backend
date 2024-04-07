package com.sarop.saropbackend.note.controller;

import com.sarop.saropbackend.note.entity.Note;
import com.sarop.saropbackend.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;


    @PostMapping("/create/{mapId}")
    public ResponseEntity<Note> createNote(@RequestBody Note note, @PathVariable String mapId) {
        Note createdNote = noteService.createNoteWithMap(note, mapId);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable String id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public List<Note> getAllNotes(){
        return noteService.getAllNotes();
    }



}
