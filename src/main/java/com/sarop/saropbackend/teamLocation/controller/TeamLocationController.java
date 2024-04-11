package com.sarop.saropbackend.teamLocation.controller;

import com.sarop.saropbackend.teamLocation.dto.TeamLocationSaveRequest;
import com.sarop.saropbackend.teamLocation.service.TeamLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teamLocation")
@RequiredArgsConstructor
public class TeamLocationController {

    private final TeamLocationService teamLocationService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN','USER')")
    public ResponseEntity<?> getAllTeamLocations(){
        return ResponseEntity.ok(teamLocationService.getAllTeamLocations());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> addTeamLocation(@RequestBody TeamLocationSaveRequest teamLocationSaveRequest){
        return ResponseEntity.ok(teamLocationService.addTeamLocation(teamLocationSaveRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> updateTeamLocation(@PathVariable String id,@RequestBody TeamLocationSaveRequest teamLocationUpdateRequest){
        return ResponseEntity.ok(teamLocationService.updateTeamLocation(id,teamLocationUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> deleteTeamLocation(@PathVariable String id){
        teamLocationService.deleteTeamLocation(id);
        return ResponseEntity.ok().build();
    }
}
