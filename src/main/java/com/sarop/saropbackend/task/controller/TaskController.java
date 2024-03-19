package com.sarop.saropbackend.task.controller;

import com.sarop.saropbackend.task.dto.TaskSaveUpdateRequest;
import com.sarop.saropbackend.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/save-task/{operationId}")
    public ResponseEntity<?> saveTask(@PathVariable String operationId, @RequestBody TaskSaveUpdateRequest taskSaveRequest){
        return ResponseEntity.ok(taskService.saveTask(operationId,taskSaveRequest));
    }

    @PutMapping("/update-task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id,@RequestBody TaskSaveUpdateRequest taskUpdateRequest){
        return ResponseEntity.ok(taskService.updateTask(id,taskUpdateRequest));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @DeleteMapping("/delete-task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id){
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
