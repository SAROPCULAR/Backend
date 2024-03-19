package com.sarop.saropbackend.user.model;


import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.token.model.Token;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable=false)
    private String firstName;
    @Column(nullable=false)
    private String lastName;
    @Column(nullable=false, unique=true)
    private String email;
    @Column(nullable=false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @ManyToMany
    private List<Operation> assignedOperations;

    @Column(nullable = false)
    private UserStatus userStatus;




}
