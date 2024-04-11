package com.sarop.saropbackend.teamLocation.repository;

import com.sarop.saropbackend.teamLocation.model.TeamLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamLocationRepository extends JpaRepository<TeamLocation, String> {

    TeamLocation findTeamLocationByName(String name);
}
