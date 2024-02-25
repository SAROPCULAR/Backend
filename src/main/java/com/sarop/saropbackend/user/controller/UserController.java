package com.sarop.saropbackend.user.controller;

import com.sarop.saropbackend.user.dto.ChangePasswordRequest;
import com.sarop.saropbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class UserController {

    private final UserService service;

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllUsers(@RequestParam(required = false) Optional<String> email,
                                           @RequestParam(required = false) Optional<String> id){
        return ResponseEntity.ok(service.findAllUser(email,id));
    }




}
