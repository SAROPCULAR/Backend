package com.sarop.saropbackend.note.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sarop.saropbackend.common.Coordinate;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@Builder
@Entity
@Table(name="note")
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(nullable=false)
    private String comment;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Coordinate coordinate;

    @ManyToOne
    @JoinColumn(name="map_id",nullable = false)
    private Map map;
}
