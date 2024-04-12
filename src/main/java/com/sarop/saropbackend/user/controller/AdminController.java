package com.sarop.saropbackend.user.controller;


import com.sarop.saropbackend.user.dto.UserSaveRequest;
import com.sarop.saropbackend.user.dto.UserUpdateRequest;
import com.sarop.saropbackend.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/user")
    public ResponseEntity<?> saveUser(@RequestBody UserSaveRequest userSaveRequest){
        return ResponseEntity.ok(adminService.saveUser(userSaveRequest));
    }

    @GetMapping("/findAllNonVerified")
    public ResponseEntity<?> findAllNonVerified(@RequestParam(required = false) Optional<String> email,
                                                @RequestParam(required = false) Optional<String> id,
                                                @RequestParam(required = false) Optional<String> name,
                                                @RequestParam(required = false) Optional<String> teamName){
        return ResponseEntity.ok(adminService.findAllNotVerifiedUsers(email, id, name, teamName));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,@RequestBody UserUpdateRequest userUpdateRequest){
        return ResponseEntity.ok(adminService.updateUser(id,userUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/verify-user/{id}")
    public ResponseEntity<?> verifyUser(@PathVariable String id){
        adminService.verifyUser(id);
        return ResponseEntity.ok().build();
    }


}
