package com.sarop.saropbackend.team.service;

import com.sarop.saropbackend.team.dto.TeamResponse;
import com.sarop.saropbackend.team.dto.TeamSaveRequest;
import com.sarop.saropbackend.team.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    Team addTeam(TeamSaveRequest teamSaveRequest);

    Team updateTeam(String id,TeamSaveRequest teamUpdateRequest);

    List<TeamResponse> findAllTeams(Optional<String> name, Optional<Integer> foundationYear, Optional<String> provinceName,
                                    Optional<String> provinceCode, Optional<String> teamLeaderName);

    void deleteTeam(String id);

}
