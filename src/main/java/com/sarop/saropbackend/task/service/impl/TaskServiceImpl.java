package com.sarop.saropbackend.task.service.impl;

import com.sarop.saropbackend.operation.repository.OperationRepository;
import com.sarop.saropbackend.task.dto.TaskSaveUpdateRequest;
import com.sarop.saropbackend.task.mapper.TaskMapper;
import com.sarop.saropbackend.task.model.Task;
import com.sarop.saropbackend.task.repository.TaskRepository;
import com.sarop.saropbackend.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;

    private OperationRepository operationRepository;

    @Override
    public Task saveTask(String operationId, TaskSaveUpdateRequest taskSaveRequest) {
        var operation = operationRepository.findById(operationId).orElseThrow();
        var task = taskMapper.TaskSaveRequesttoTask(taskSaveRequest);
        task.setOperation(operation);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(String id,TaskSaveUpdateRequest taskUpdateRequest) {
        var task = taskRepository.findById(id).orElseThrow();
        task = taskMapper.TaskUpdateRequestToTask(task,taskUpdateRequest);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
