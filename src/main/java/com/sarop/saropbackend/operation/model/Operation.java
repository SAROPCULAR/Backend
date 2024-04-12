package com.sarop.saropbackend.operation.model;

import com.sarop.saropbackend.category.model.Category;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.team.model.Team;
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
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private int operationNumber;

    @Column
    private String operationDate;

    @Column
    private String name;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Team team;

    @ManyToMany
    @JoinTable(
            name = "operation_map",
            joinColumns = @JoinColumn(name = "operation_id"),
            inverseJoinColumns = @JoinColumn(name = "map_id")
    )
    private List<Map> maps;
}
