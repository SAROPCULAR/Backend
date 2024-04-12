package com.sarop.saropbackend.restapi.entity;

import com.sarop.saropbackend.operation.model.Operation;
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

    @ManyToMany
    private List<Operation> operation;


}