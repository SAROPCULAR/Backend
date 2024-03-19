package com.sarop.saropbackend.team.controller;

import com.sarop.saropbackend.team.dto.TeamSaveRequest;
import com.sarop.saropbackend.team.dto.TeamUpdateRequest;
import com.sarop.saropbackend.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/save-team")
    public ResponseEntity<?> saveTeam(@RequestBody TeamSaveRequest teamSaveRequest){
        return ResponseEntity.ok(teamService.saveTeam(teamSaveRequest));
    }

    @PutMapping("/update-team/{id}")
    public ResponseEntity<?> updateTeam(@PathVariable String id,@RequestBody TeamUpdateRequest teamUpdateRequest){
        return ResponseEntity.ok(teamService.updateTeam(id,teamUpdateRequest));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTeams(){
        return ResponseEntity.ok(teamService.getTeams());
    }

    @DeleteMapping("/delete-team/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable String id){
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
}
