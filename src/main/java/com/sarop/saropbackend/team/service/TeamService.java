package com.sarop.saropbackend.team.service;

import com.sarop.saropbackend.team.dto.TeamSaveRequest;
import com.sarop.saropbackend.team.dto.TeamUpdateRequest;
import com.sarop.saropbackend.team.model.Team;

import java.util.List;


public interface TeamService {
    Team saveTeam(TeamSaveRequest teamSaveRequest);
    Team updateTeam(String id, TeamUpdateRequest teamUpdateRequest);
    List<Team> getTeams();
    void deleteTeam(String id);
}
