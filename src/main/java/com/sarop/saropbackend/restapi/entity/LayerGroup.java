package com.sarop.saropbackend.restapi.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sarop.saropbackend.restapi.dto.Mode;
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
@Table(name = "layer_groups")
public class LayerGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode")
    private Mode mode;

    @Column(name = "title")
    private String title;

    @Column(name = "abstract_text", length = 1024)
    private String abstractText;


    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;


    @ManyToMany
    @JoinTable(
            name = "layer_group_layers",
            joinColumns = @JoinColumn(name = "layer_group_id"),
            inverseJoinColumns = @JoinColumn(name = "layer_id")
    )
    private List<Map> layers;

}
