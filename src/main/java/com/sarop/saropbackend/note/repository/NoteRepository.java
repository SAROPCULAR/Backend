package com.sarop.saropbackend.note.repository;

import com.sarop.saropbackend.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,String> {
    List<Note> getAllByMap_Id(String mapId);
}
