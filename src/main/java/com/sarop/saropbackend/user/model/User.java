package com.sarop.saropbackend.user.model;


import com.fasterxml.jackson.annotation.*;
import com.sarop.saropbackend.note.model.Note;
import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.token.model.Token;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @Column(nullable = false)
    private String name;
    @Column(nullable=false, unique=true)
    private String email;
    @Column(nullable=false)
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany( fetch = FetchType.EAGER)
    private List<Token> tokens;

    @Column
    private UserStatus status;

    @ManyToOne()
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Note> notes;



}
