package com.sarop.saropbackend.operation.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sarop.saropbackend.category.model.Category;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.team.model.Team;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne()
    @JoinColumn(name="team_id")
    private Team team;

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "operation_map",
            joinColumns = @JoinColumn(name = "operation_id"),
            inverseJoinColumns = @JoinColumn(name = "map_id")
    )
    private List<Map> maps;

    @Override
    public String toString() {
        String mapNames = maps != null ? maps.stream()
                .map(Map::getMapName)
                .collect(Collectors.toList())
                .toString()
                : "[]";
        return "Operation{" +
                "id='" + id + '\'' +
                ", operationNumber=" + operationNumber +
                ", operationDate='" + operationDate + '\'' +
                ", name='" + name + '\'' +
                ", categoryName='" + (category != null ? category.getId() : null) + '\'' +
                ", teamName='" + (team != null ? team.getId() : null) + '\'' +
                ", mapNames=" + mapNames +
                '}';
    }
}
