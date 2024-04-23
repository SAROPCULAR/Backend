package com.sarop.saropbackend.restapi.entity;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sarop.saropbackend.note.entity.Note;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Polygon;


import java.io.Serializable;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="map")
public class Map  {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.UUID)
    private String mapId;
    @Column(nullable=false)
    private String mapName;
    @Column(nullable=false)
    private String mapType;
    @Column(nullable=false)
    private String fileUrl;

    @ManyToOne
    private Workspace workspace;
    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
    private Set<Note> noteList;
    @Column
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    private Polygon polygon;

}


