package com.sarop.saropbackend.team.controller;

import com.sarop.saropbackend.team.dto.TeamSaveRequest;
import com.sarop.saropbackend.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> saveTeam(@RequestBody TeamSaveRequest teamSaveRequest){
        return ResponseEntity.ok(teamService.addTeam(teamSaveRequest));
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN','USER')")
    public ResponseEntity<?> getTeams(
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Integer> foundationYear,
            @RequestParam(required = false) Optional<String> provinceName,
            @RequestParam(required = false) Optional<String> provinceCode,
            @RequestParam(required = false) Optional<String> teamLeaderName
            )
    {
        return ResponseEntity.ok(teamService.findAllTeams(name, foundationYear, provinceName, provinceCode, teamLeaderName));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> updateTeam(@PathVariable String id,@RequestBody TeamSaveRequest teamUpdateRequest){
        return ResponseEntity.ok(teamService.updateTeam(id,teamUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> deleteTeam(@PathVariable String id){
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
}
