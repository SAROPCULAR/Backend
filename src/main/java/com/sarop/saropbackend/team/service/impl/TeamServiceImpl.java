package com.sarop.saropbackend.team.service.impl;

import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.team.dto.TeamSaveRequest;
import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.team.repository.TeamRepository;
import com.sarop.saropbackend.team.service.TeamService;
import com.sarop.saropbackend.teamLocation.model.TeamLocation;
import com.sarop.saropbackend.teamLocation.repository.TeamLocationRepository;
import com.sarop.saropbackend.user.model.Role;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final TeamLocationRepository teamLocationRepository;

    private final UserRepository userRepository;
    @Override
    public Team addTeam(TeamSaveRequest teamSaveRequest) {
        var team = Team.builder().id(Util.generateUUID()).name(teamSaveRequest.getName())
                .foundationYear(teamSaveRequest.getFoundationYear())
                .provinceCode(teamSaveRequest.getProvinceCode())
                .provinceName(teamSaveRequest.getProvinceName())
                .phoneDescription(teamSaveRequest.getPhoneDescription())
                .teamLocations(new ArrayList<TeamLocation>())
                .members(new ArrayList<User>()).build();
        User teamLeader = userRepository.findByEmail(teamSaveRequest.getTeamLeaderEmail()).orElseThrow();
        teamLeader.setRole(Role.OPERATION_ADMIN);
        team.setTeamLeader(teamLeader);

        for(String name : teamSaveRequest.getTeamLocations()){
            TeamLocation teamLocation = teamLocationRepository.findTeamLocationByName(name);
            team.getTeamLocations().add(teamLocation);
        }
        for(String email: teamSaveRequest.getUsers()){
            User user = userRepository.findByEmail(email).orElseThrow();
            team.getMembers().add(user);
        }
        teamRepository.save(team);
        return team;
    }

    @Override
    public Team updateTeam(String id,TeamSaveRequest teamUpdateRequest) {
        Team team = teamRepository.findById(id).orElseThrow();
        team.setName(teamUpdateRequest.getName());
        team.setFoundationYear(teamUpdateRequest.getFoundationYear());
        team.setProvinceCode(teamUpdateRequest.getProvinceCode());
        team.setProvinceName(teamUpdateRequest.getProvinceName());
        team.setPhoneDescription(teamUpdateRequest.getPhoneDescription());
        User teamLeader = userRepository.findByEmail(teamUpdateRequest.getTeamLeaderEmail()).orElseThrow();
        if(teamLeader.equals(team.getTeamLeader())){
            team.getTeamLeader().setRole(Role.USER);
            teamLeader.setRole(Role.OPERATION_ADMIN);
            team.setTeamLeader(teamLeader);
        }
        List<TeamLocation> teamLocations = new ArrayList<TeamLocation>();
        List<User> members = new ArrayList<User>();
        for(String name : teamUpdateRequest.getTeamLocations()){
            TeamLocation teamLocation = teamLocationRepository.findTeamLocationByName(name);
            teamLocations.add(teamLocation);
        }
        for(String email: teamUpdateRequest.getUsers()){
            User user = userRepository.findByEmail(email).orElseThrow();
            members.add(user);
        }
        team.setTeamLocations(teamLocations);
        team.setMembers(members);
        teamRepository.save(team);
        return team;
    }


    @Override
    public List<Team> findAllTeams(Optional<String> name, Optional<Integer> foundationYear, Optional<String> provinceName,
                                   Optional<String> provinceCode, Optional<String> teamLeaderName
                                   ) {
        List<Team> teams = teamRepository.findAll().stream().filter(team ->
                        (!name.isPresent() || team.getName().equals(name)) ||
                                (!foundationYear.isPresent() || foundationYear.equals(team.getFoundationYear())) ||
                                (!provinceName.isPresent() || team.getProvinceName().equals(provinceName)) ||
                                (!provinceCode.isPresent() || team.getProvinceCode().equals(provinceCode)) ||
                                (!teamLeaderName.isPresent() || (team.getTeamLeader().getFirstName() + " " + team.getTeamLeader().getLastName()).equals(teamLeaderName))
                ).collect(Collectors.toList());
        return teams;
    }

    @Override
    public void deleteTeam(String id) {
        teamRepository.deleteById(id);
    }
}
