package com.sarop.saropbackend.note.controller;

import com.sarop.saropbackend.note.dto.NoteAddRequest;
import com.sarop.saropbackend.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> getAllNotesByMapId(@PathVariable String id){
        return ResponseEntity.ok(noteService.getAllNotesByMapId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> addNote(@RequestBody NoteAddRequest noteAddRequest){
        return ResponseEntity.ok(noteService.addNote(noteAddRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> deleteNote(@PathVariable String id){
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}
