package com.sarop.saropbackend.operation.controller;

import com.sarop.saropbackend.operation.dto.OperationSaveRequest;

import com.sarop.saropbackend.operation.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operation")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> getAllOperations(){
        return ResponseEntity.ok(operationService.getAllOperations());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> addOperation(@RequestBody OperationSaveRequest operationSaveRequest){
        return ResponseEntity.ok(operationService.addOperation(operationSaveRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> updateOperation(@PathVariable String id, @RequestBody OperationSaveRequest operationUpdateRequest){
        return ResponseEntity.ok(operationService.updateOperation(id,operationUpdateRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> deleteOperation(@PathVariable String id){
        operationService.deleteOperation(id);
        return ResponseEntity.ok().build();
    }
}
