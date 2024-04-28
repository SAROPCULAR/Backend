package com.sarop.saropbackend.teamLocation.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sarop.saropbackend.team.model.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "team_location")
public class TeamLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(length = 500)
    private String name;

    @Column
    private String provinceCode;

    @Column
    private String provinceName;

    @Column
    private String countyName;

    @Column(length = 500)
    private String address;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column(length = 500)
    private String description;

    @Column
    private String phoneNumber;

    @Column
    private String secondPhoneNumber;

    @Column
    private String thirdPhoneNumber;

    @Column
    private String faxNumber;





}
