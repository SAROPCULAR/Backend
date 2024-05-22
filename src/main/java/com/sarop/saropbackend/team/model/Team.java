package com.sarop.saropbackend.team.model;

import com.fasterxml.jackson.annotation.*;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.teamLocation.model.TeamLocation;
import com.sarop.saropbackend.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "team")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @OneToOne()
    private User teamLeader;


    @Column
    private String phoneDescription;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<TeamLocation> teamLocations;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<User> members;

    @OneToMany(mappedBy = "team",fetch = FetchType.EAGER)
    private List<Operation> operations;


}
