package com.sarop.saropbackend.user.controller;

import com.sarop.saropbackend.user.dto.ChangePasswordRequest;
import com.sarop.saropbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public ResponseEntity<?> findAllUsers(){
        return ResponseEntity.ok(service.findAllUser());
    }

    // Listeleme sayfasında filtreleme yapmak
    @GetMapping("/find")
    public ResponseEntity<?> findUser(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String email
    ){
        if (id != null) {
            return ResponseEntity.ok(service.findById(id));
        } else if (email != null) {
            return ResponseEntity.ok(service.findByEmail(email));
        } else {
            return ResponseEntity.badRequest().body("Please provide either id or email parameter");
        }
    }


}
