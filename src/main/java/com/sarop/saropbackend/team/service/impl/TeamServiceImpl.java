package com.sarop.saropbackend.team.service.impl;

import com.sarop.saropbackend.common.Util;

import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operation.repository.OperationRepository;
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

    private final OperationRepository operationRepository;


    @PostConstruct
    @Transactional
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
        // Create team entity
        var team = Team.builder()
                .id(Util.generateUUID())
                .name(teamSaveRequest.getName())
                .foundationYear(teamSaveRequest.getFoundationYear())
                .provinceCode(teamSaveRequest.getProvinceCode())
                .provinceName(teamSaveRequest.getProvinceName())
                .phoneDescription(teamSaveRequest.getPhoneDescription())
                .teamLocations(new ArrayList<>())
                .members(new ArrayList<>()).operations(new ArrayList<>())
                .build();

        // Save the team
        team = teamRepository.save(team);

        // Find team leader and set role
        User teamLeader = userRepository.findByEmail(teamSaveRequest.getTeamLeaderEmail()).orElseThrow();
        teamLeader.setRole(Role.OPERATION_ADMIN);

        // Create a list to store members
        List<User> membersToSave = new ArrayList<>();

        // Add users to the team
        for (String email : teamSaveRequest.getUsers()) {
            User user = userRepository.findByEmail(email).orElseThrow();
            team.getMembers().add(user);
            user.setTeam(team);
            membersToSave.add(user);
        }

        // Save the team members
        userRepository.saveAll(membersToSave);

        // Add team locations
        for (String id : teamSaveRequest.getTeamLocations()) {
            TeamLocation teamLocation = teamLocationRepository.findById(id).orElseThrow();
            teamLocation.setTeam(team);
            team.getTeamLocations().add(teamLocation);
        }

        // Save the team locations
        teamLocationRepository.saveAll(team.getTeamLocations());

        // Set team leader
        team.setTeamLeader(teamLeader);
        teamLeader.setTeam(team);
        userRepository.save(teamLeader);

        return team;
    }




    @Override
    @Transactional
    public Team updateTeam(String id, TeamSaveRequest teamUpdateRequest) {
        Team team = teamRepository.findById(id).orElseThrow();
        team.setName(teamUpdateRequest.getName());
        team.setFoundationYear(teamUpdateRequest.getFoundationYear());
        team.setProvinceCode(teamUpdateRequest.getProvinceCode());
        team.setProvinceName(teamUpdateRequest.getProvinceName());
        team.setPhoneDescription(teamUpdateRequest.getPhoneDescription());

        User teamLeader = userRepository.findByEmail(teamUpdateRequest.getTeamLeaderEmail()).orElseThrow();
        if (!teamLeader.equals(team.getTeamLeader())) {
            team.getTeamLeader().setRole(Role.USER);
            teamLeader.setRole(Role.OPERATION_ADMIN);
            team.setTeamLeader(teamLeader);
        }

        // Update team locations
        for(TeamLocation teamLocation: team.getTeamLocations()){
            teamLocation.setTeam(null);
        }
        team.getTeamLocations().clear();
        for (String locationId : teamUpdateRequest.getTeamLocations()) {
            TeamLocation teamLocation = teamLocationRepository.findById(locationId).orElseThrow();
            team.getTeamLocations().add(teamLocation);
            teamLocation.setTeam(team);
        }

        // Update team members
        for(User user: team.getMembers()){
            user.setTeam(null);
        }
        team.getMembers().clear();
        for (String email : teamUpdateRequest.getUsers()) {
            User user = userRepository.findByEmail(email).orElseThrow();
            team.getMembers().add(user);
            user.setTeam(team);
        }

        // Save the updated team with all its relationships
        team = teamRepository.save(team);

        return team;
    }



    @Override
    public List<Team> findAllTeams(Optional<String> name, Optional<Integer> foundationYear, Optional<String> provinceName,
                                   Optional<String> provinceCode, Optional<String> teamLeaderName) {

        List<Team> teams = teamRepository.findAll().stream().filter(team ->
                (name.isEmpty() || team.getName().equals(name.get())) &&
                        (foundationYear.isEmpty() || foundationYear.get().equals(team.getFoundationYear())) &&
                        (provinceName.isEmpty() || team.getProvinceName().equals(provinceName.get())) &&
                        (provinceCode.isEmpty() || team.getProvinceCode().equals(provinceCode.get())) &&
                        (teamLeaderName.isEmpty() || team.getTeamLeader().getName().equals(teamLeaderName.get()))
        ).collect(Collectors.toList());

        return teams;
    }


    @Override
    @Transactional
    public void deleteTeam(String id) {
            Team team = teamRepository.findById(id).orElseThrow();

            // Explicitly delete TeamLocations associated with the Team
            for (TeamLocation location : team.getTeamLocations()) {
                location.setTeam(null);
                teamLocationRepository.save(location);
            }

            // Explicitly delete Operations associated with the Team
            for (Operation operation : team.getOperations()) {
                operation.setTeam(null);
                operationRepository.save(operation);
            }
            User teamLeader = team.getTeamLeader();
            teamLeader.setRole(Role.USER);
            // Remove team reference from Users (avoid deleting Users)
            for (User user : team.getMembers()) {
                user.setTeam(null);
                userRepository.save(user); // Update the User entity
            }

            // Finally, delete the Team entity
            teamRepository.delete(team);


    }
}
