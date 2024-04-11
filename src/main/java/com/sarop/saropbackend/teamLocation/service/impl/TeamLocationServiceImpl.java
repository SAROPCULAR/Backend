package com.sarop.saropbackend.teamLocation.service.impl;


import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.team.repository.TeamRepository;
import com.sarop.saropbackend.teamLocation.dto.TeamLocationSaveRequest;
import com.sarop.saropbackend.teamLocation.model.TeamLocation;
import com.sarop.saropbackend.teamLocation.repository.TeamLocationRepository;
import com.sarop.saropbackend.teamLocation.service.TeamLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamLocationServiceImpl implements TeamLocationService {

    private final TeamLocationRepository teamLocationRepository;
    private final TeamRepository teamRepository;
    @Override
    public TeamLocation addTeamLocation(TeamLocationSaveRequest teamLocationSaveRequest) {
        var teamLocation = TeamLocation.builder().id(Util.generateUUID()).name(teamLocationSaveRequest.getName())
                .provinceCode(teamLocationSaveRequest.getProvinceCode())
                .provinceName(teamLocationSaveRequest.getProvinceName())
                .countyName(teamLocationSaveRequest.getCountyName())
                .address(teamLocationSaveRequest.getAddress())
                .latitude(teamLocationSaveRequest.getLatitude())
                .longitude(teamLocationSaveRequest.getLongitude())
                .phoneNumber(teamLocationSaveRequest.getPhoneNumber())
                .secondPhoneNumber(teamLocationSaveRequest.getSecondPhoneNumber())
                .thirdPhoneNumber(teamLocationSaveRequest.getThirdPhoneNumber())
                .faxNumber(teamLocationSaveRequest.getFaxNumber()).build();
        return teamLocationRepository.save(teamLocation);
    }

    @Override
    public TeamLocation updateTeamLocation(String id, TeamLocationSaveRequest teamLocationUpdateRequest) {

        TeamLocation teamLocation = teamLocationRepository.findById(id).orElseThrow();
        teamLocation.setName(teamLocationUpdateRequest.getName());
        teamLocation.setProvinceCode(teamLocationUpdateRequest.getProvinceCode());
        teamLocation.setProvinceName(teamLocationUpdateRequest.getProvinceName());
        teamLocation.setCountyName(teamLocationUpdateRequest.getCountyName());
        teamLocation.setAddress(teamLocationUpdateRequest.getAddress());
        teamLocation.setLatitude(teamLocationUpdateRequest.getLatitude());
        teamLocation.setLongitude(teamLocationUpdateRequest.getLongitude());
        teamLocation.setPhoneNumber(teamLocationUpdateRequest.getPhoneNumber());
        teamLocation.setSecondPhoneNumber(teamLocationUpdateRequest.getSecondPhoneNumber());
        teamLocation.setThirdPhoneNumber(teamLocationUpdateRequest.getThirdPhoneNumber());
        teamLocation.setFaxNumber(teamLocationUpdateRequest.getFaxNumber());
        return teamLocationRepository.save(teamLocation);
    }

    @Override
    public List<TeamLocation> getAllTeamLocations() {
        return teamLocationRepository.findAll();
    }

    @Override
    public void deleteTeamLocation(String id) {
        teamLocationRepository.deleteById(id);
    }
}
