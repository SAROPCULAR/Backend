package com.sarop.saropbackend.operation.controller;

import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.operation.dto.OperationUpdateRequest;
import com.sarop.saropbackend.operation.service.impl.OperationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController {
    private final OperationServiceImpl operationService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllOperations(){
        return ResponseEntity.ok(operationService.getAllOperations());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        return ResponseEntity.ok(operationService.getOperationById(id));
    }
    @GetMapping("/find/{user}")
    public ResponseEntity<?> findByUserId(@PathVariable String userId){
        return ResponseEntity.ok(operationService.getOperationById(userId));
    }


    @PostMapping("/save-operation")
    public ResponseEntity<?> saveOperation(@RequestBody OperationSaveRequest operationSaveRequest){
        return ResponseEntity.ok(operationService.saveOperation(operationSaveRequest));
    }

    @PutMapping("/update-operation/{id}")
    public ResponseEntity<?> updateOperation(@PathVariable String id,@RequestBody OperationUpdateRequest operationUpdateRequest){
        return ResponseEntity.ok(operationService.updateOperation(operationUpdateRequest,id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOperation(@PathVariable String id){
        operationService.deleteOperation(id);
        return ResponseEntity.ok().build();
    }


}