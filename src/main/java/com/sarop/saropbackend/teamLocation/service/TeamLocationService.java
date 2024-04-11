package com.sarop.saropbackend.teamLocation.service;

import com.sarop.saropbackend.teamLocation.dto.TeamLocationSaveRequest;
import com.sarop.saropbackend.teamLocation.model.TeamLocation;

import java.util.List;

public interface TeamLocationService {

    TeamLocation addTeamLocation(TeamLocationSaveRequest teamLocationSaveRequest);

    TeamLocation updateTeamLocation(String id, TeamLocationSaveRequest teamLocationUpdateRequest);

    List<TeamLocation> getAllTeamLocations();

    void deleteTeamLocation(String id);
}
