package com.sarop.saropbackend.locations.model;

import com.sarop.saropbackend.team.model.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "locations")
public class OperationalTeamLocations {

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
    private String firstPhoneNumber;

    @Column
    private String secondPhoneNumber;

    @Column
    private String thirdPhoneNumber;

    @Column
    private String faxNumber;



}
