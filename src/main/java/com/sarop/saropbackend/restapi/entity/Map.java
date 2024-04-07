package com.sarop.saropbackend.restapi.entity;

import com.sarop.saropbackend.note.entity.Note;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="map")
public class Map {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable=false)
    private String mapName;
    @Column(nullable=false)
    private String mapType;
    @Column(nullable=false)
    private String fileUrl;

    @ManyToOne
    private Workspace workspace;
    @OneToMany
    private List<Note> noteList;


}