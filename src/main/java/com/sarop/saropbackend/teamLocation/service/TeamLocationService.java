package com.sarop.saropbackend.teamLocation.service;

import com.sarop.saropbackend.teamLocation.dto.TeamLocationResponse;
import com.sarop.saropbackend.teamLocation.dto.TeamLocationSaveRequest;
import com.sarop.saropbackend.teamLocation.model.TeamLocation;

import java.util.List;
import java.util.Optional;

public interface TeamLocationService {

    TeamLocation addTeamLocation(TeamLocationSaveRequest teamLocationSaveRequest);

    TeamLocation updateTeamLocation(String id, TeamLocationSaveRequest teamLocationUpdateRequest);

    List<TeamLocationResponse> getAllTeamLocations(Optional<String> teamName, Optional<String> name,
                                                   Optional<String> provinceCode, Optional<String> provinceName,
                                                   Optional<String> countyName);

    void deleteTeamLocation(String id);
}
