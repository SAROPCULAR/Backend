package com.sarop.saropbackend.user.controller;


import com.sarop.saropbackend.user.dto.UserSaveRequest;
import com.sarop.saropbackend.user.dto.UserUpdateRequest;
import com.sarop.saropbackend.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/save-user")
    public ResponseEntity<?> saveUser(@RequestBody UserSaveRequest userSaveRequest){
        return ResponseEntity.ok(adminService.saveUser(userSaveRequest));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,@RequestBody UserUpdateRequest userUpdateRequest){
        return ResponseEntity.ok(adminService.updateUser(id,userUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }


}
