package com.sarop.saropbackend.teamLocation.model;

import com.sarop.saropbackend.team.model.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column
    private String name;

    @Column
    private String provinceCode;

    @Column
    private String provinceName;

    @Column
    private String countyName;

    @Column
    private String address;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column
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
