package com.sarop.saropbackend.team.service.impl;

import com.sarop.saropbackend.common.Util;

import com.sarop.saropbackend.team.dto.TeamSaveRequest;
import com.sarop.saropbackend.team.dto.apimodels.OperationalTeamApiModel;
import com.sarop.saropbackend.team.dto.apimodels.OperationalTeamLocation;

import com.sarop.saropbackend.team.dto.apiresponse.OperationalTeamResponse;
import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.team.repository.TeamRepository;
import com.sarop.saropbackend.team.service.TeamService;
import com.sarop.saropbackend.teamLocation.model.TeamLocation;
import com.sarop.saropbackend.teamLocation.repository.TeamLocationRepository;
import com.sarop.saropbackend.user.model.Role;
import com.sarop.saropbackend.user.model.User;
import com.sarop.saropbackend.user.model.UserStatus;
import com.sarop.saropbackend.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    //@Transactional
    public void loadDataFromApi() {
        String apiUrl = "https://portal.akut.org.tr/api/webpage/getteamlist";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OperationalTeamResponse> responseEntity = restTemplate.getForEntity(apiUrl, OperationalTeamResponse.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            OperationalTeamResponse response = responseEntity.getBody();
            if (response != null && response.getOperationalTeamApiModels() != null) {
                for (OperationalTeamApiModel apiModel : response.getOperationalTeamApiModels()) {
                    Team team = new Team();
                    team.setId(apiModel.getOperationalTeamId());
                    team.setName(apiModel.getName());
                    team.setFoundationYear(apiModel.getFoundationYear());
                    team.setProvinceCode(apiModel.getProvinceCode());
                    team.setProvinceName(apiModel.getProvinceName());
                    team.setPhoneDescription(apiModel.getPhoneDescription());
                    teamRepository.save(team);
                    User teamLeader = new User();
                    teamLeader.setId(Util.generateUUID());
                    teamLeader.setName(apiModel.getTeamLeaderName());
                    teamLeader.setEmail(apiModel.getTeamLeaderEMail());
                    teamLeader.setRole(Role.OPERATION_ADMIN);
                    teamLeader.setStatus(UserStatus.VERIFIED);
                    String password = Util.generateRandomPassword(16);
                    teamLeader.setPassword(passwordEncoder.encode(password));
                    teamLeader.setTeam(team);
                    team.setTeamLeader(teamLeader);

                    System.out.println("User is saved with email: " + teamLeader.getEmail() + " and password: " + password);
                    List<TeamLocation> locations = new ArrayList<>();
                    // Map locations
                    for (OperationalTeamLocation locationApiModel : apiModel.getOperationalTeamLocations()) {
                        TeamLocation location = new TeamLocation();
                        location.setId(locationApiModel.getOperationalTeamLocationId());

                        location.setName(locationApiModel.getName());
                        location.setProvinceCode(locationApiModel.getProvinceCode());
                        location.setProvinceName(locationApiModel.getProvinceName());
                        location.setCountyName(locationApiModel.getCountyName());
                        location.setAddress(locationApiModel.getAddress());
                        location.setLatitude(locationApiModel.getLatitude());
                        location.setLongitude(locationApiModel.getLongitude());
                        location.setDescription(locationApiModel.getDescription());
                        location.setPhoneNumber(locationApiModel.getFirstPhoneNumber());
                        location.setSecondPhoneNumber(locationApiModel.getSecondPhoneNumber());
                        location.setThirdPhoneNumber(locationApiModel.getThirdPhoneNumber());
                        location.setFaxNumber(locationApiModel.getFaxNumber());
                        location.setTeam(team);
                        locations.add(location);
                    }
                    team.setTeamLocations(locations);
                    teamRepository.save(team);

                }
            }
        }
    }
    @Override
    @Transactional
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
    @Transactional
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
                                (!teamLeaderName.isPresent() || (team.getTeamLeader().getName()).equals(teamLeaderName))
                ).collect(Collectors.toList());
        return teams;
    }

    @Override
    public void deleteTeam(String id) {
        teamRepository.deleteById(id);
    }
}
