package com.sarop.saropbackend.polygon.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sarop.saropbackend.common.Coordinate;
import com.sarop.saropbackend.restapi.entity.Map;
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
@Table(name="polygon")
public class Polygon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    @OneToMany(fetch = FetchType.EAGER)
    private List<Coordinate> coordinates;

    @ManyToOne()
    @JoinColumn(name="map_id",nullable = false)
    private Map map;

}
