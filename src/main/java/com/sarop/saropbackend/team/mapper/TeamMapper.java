package com.sarop.saropbackend.team.mapper;

import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.locations.model.OperationalTeamLocations;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.team.dto.TeamSaveRequest;
import com.sarop.saropbackend.team.dto.TeamUpdateRequest;
import com.sarop.saropbackend.team.model.Team;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TeamMapper {
    public Team teamSaveRequestToTeam(TeamSaveRequest teamSaveRequest){
        var team = Team.builder().id(Util.generateUUID()).name(teamSaveRequest.getName())
                .description(teamSaveRequest.getDescription()).foundationYear(teamSaveRequest.getFoundationYear())
                .provinceCode(teamSaveRequest.getProvinceCode()).provinceName(teamSaveRequest.getProvinceName())
                .teamLeader(teamSaveRequest.getTeamLeader()).members(teamSaveRequest.getMembers())
                .operationalTeamLocations(new ArrayList<OperationalTeamLocations>())
                .operations(new ArrayList<Operation>()).build();
        return team;
    }
    public Team teamUpdateRequestToTeam(Team team, TeamUpdateRequest teamUpdateRequest){
        team.setName(teamUpdateRequest.getName());
        team.setDescription(teamUpdateRequest.getDescription());
        team.setFoundationYear(teamUpdateRequest.getFoundationYear());
        team.setProvinceCode(teamUpdateRequest.getProvinceCode());
        team.setProvinceName(teamUpdateRequest.getProvinceName());
        team.setTeamLeader(teamUpdateRequest.getTeamLeader());
        team.setMembers(teamUpdateRequest.getMembers());
        team.setOperations(teamUpdateRequest.getOperations());
        team.setOperationalTeamLocations(teamUpdateRequest.getOperationalTeamLocations());
        return team;
    }
}
