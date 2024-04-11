package com.sarop.saropbackend.team.service;

import com.sarop.saropbackend.team.dto.TeamSaveRequest;
import com.sarop.saropbackend.team.model.Team;

import java.util.List;

public interface TeamService {

    Team addTeam(TeamSaveRequest teamSaveRequest);

    Team updateTeam(String id,TeamSaveRequest teamUpdateRequest);

    List<Team> findAllTeams();

    void deleteTeam(String id);

}
