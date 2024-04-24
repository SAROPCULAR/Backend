package com.sarop.saropbackend.team.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sarop.saropbackend.teamLocation.model.TeamLocation;
import com.sarop.saropbackend.user.model.User;
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
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column
    private int foundationYear;

    @Column
    private String provinceCode;

    @Column
    private String provinceName;

    @OneToOne(cascade = CascadeType.ALL)
    private User teamLeader;


    @Column
    private String phoneDescription;

    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TeamLocation> teamLocations;


    @OneToMany(mappedBy = "team",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> members;
}
