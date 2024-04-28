package com.sarop.saropbackend.restapi.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sarop.saropbackend.note.model.Note;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.polygon.model.Polygon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="map")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable=false)
    private String mapName;
    @Column(nullable=false)
    private String mapType;
    @Column(nullable=false)
    private String fileUrl;

    @ManyToOne()
    private Workspace workspace;

    @ManyToMany()
    private List<Operation> operations;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Note> notes;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Polygon> polygons;




}