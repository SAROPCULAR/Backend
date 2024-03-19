package com.sarop.saropbackend.team.service.impl;

import com.sarop.saropbackend.team.dto.TeamSaveRequest;
import com.sarop.saropbackend.team.dto.TeamUpdateRequest;
import com.sarop.saropbackend.team.mapper.TeamMapper;
import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.team.repository.TeamRepository;
import com.sarop.saropbackend.team.service.TeamService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public Team saveTeam(TeamSaveRequest teamSaveRequest){
        var team = teamMapper.teamSaveRequestToTeam(teamSaveRequest);
        return teamRepository.save(team);
    }

    public Team updateTeam(String id, TeamUpdateRequest teamUpdateRequest){
        var team = teamRepository.findById(id).orElseThrow();
        team = teamMapper.teamUpdateRequestToTeam(team,teamUpdateRequest);
        return teamRepository.save(team);
    }

    public List<Team> getTeams(){
        return teamRepository.findAll();
    }

    public void deleteTeam(String id){

        teamRepository.deleteById(id);
    }




}
