package com.sarop.saropbackend.team.model;

import com.sarop.saropbackend.locations.model.OperationalTeamLocations;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer foundationYear;

    @Column(nullable = false)
    private String provinceCode;

    @Column(nullable = false)
    private String provinceName;

    @OneToOne
    @JoinColumn(name = "team_leader_id")
    private User teamLeader;

    @OneToMany(mappedBy = "team")
    private List<User> members;
    
    @OneToMany(mappedBy = "team")
    private List<OperationalTeamLocations> operationalTeamLocations;

    @OneToMany
    private List<Operation> operations;
}
