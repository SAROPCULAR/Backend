package com.sarop.saropbackend.note.service.impl;

import com.sarop.saropbackend.common.Coordinate;
import com.sarop.saropbackend.common.CoordinateRepository;
import com.sarop.saropbackend.note.dto.NoteAddRequest;
import com.sarop.saropbackend.note.model.Note;
import com.sarop.saropbackend.note.repository.NoteRepository;
import com.sarop.saropbackend.note.service.NoteService;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.restapi.repository.MapRepository;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final MapRepository mapRepository;

    private final UserRepository userRepository;

    private final CoordinateRepository coordinateRepository;
    @Override
    public List<Note> getAllNotesByMapId(String mapId) {
        return noteRepository.getAllByMap_Id(mapId);
    }

    @Override
    @Transactional
    public Note addNote(NoteAddRequest noteAddRequest) {
        Map map = mapRepository.findById(noteAddRequest.getMapId()).orElseThrow();
        User user = userRepository.findByEmail(noteAddRequest.getUserEmail()).orElseThrow();
        Note note = Note.builder().user(user).map(map).comment(noteAddRequest.getComment()).coordinate(new Coordinate())
                .build();
        note.getCoordinate().setX(noteAddRequest.getX());
        note.getCoordinate().setY(noteAddRequest.getY());
        coordinateRepository.save(note.getCoordinate());
        noteRepository.save(note);
        user.getNotes().add(note);
        map.getNotes().add(note);
       // userRepository.save(user);
      //  mapRepository.save(map);
        return note;
    }

    @Override
    public void deleteNote(String id) {
         Note note = noteRepository.findById(id).orElseThrow();
         Map map = note.getMap();
         User user = note.getUser();
         map.getNotes().remove(note);
         user.getNotes().remove(note);
         //mapRepository.save(map);
         //userRepository.save(user);
         noteRepository.delete(note);
    }
}
