package com.sarop.saropbackend.operation.controller;

import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.operation.dto.OperationUpdateRequest;
import com.sarop.saropbackend.operation.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @PostMapping("/save-operation")
    public ResponseEntity<?> saveOperation(@RequestBody OperationSaveRequest operationSaveRequest){
        return ResponseEntity.ok(operationService.saveOperation(operationSaveRequest));
    }

    @PutMapping("/update-operation/{id}")
    public ResponseEntity<?> updateOperation(@PathVariable String id,@RequestBody OperationUpdateRequest operationUpdateRequest){
        return ResponseEntity.ok(operationService.updateOperation(id,operationUpdateRequest));
    }

    @GetMapping("/getOperations")
    public ResponseEntity<?> getAllOperations(){
        return ResponseEntity.ok(operationService.getAllOperation());
    }

    @DeleteMapping("/delete-operation/{id}")
    public ResponseEntity<?> deleteOperation(@PathVariable String id){
        operationService.deleteOperation(id);
        return ResponseEntity.ok().build();
    }
}
