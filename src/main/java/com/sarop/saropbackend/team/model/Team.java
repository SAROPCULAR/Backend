package com.sarop.saropbackend.team.model;

import com.sarop.saropbackend.teamLocation.model.TeamLocation;
import com.sarop.saropbackend.user.model.User;
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

    @OneToOne
    private User teamLeader;


    @Column
    private String phoneDescription;

    @OneToMany(mappedBy = "team")

    private List<TeamLocation> teamLocations;

    @OneToMany
    private List<User> members;
}
