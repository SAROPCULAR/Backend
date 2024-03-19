package com.sarop.saropbackend.task.service;

import com.sarop.saropbackend.task.dto.TaskSaveUpdateRequest;
import com.sarop.saropbackend.task.model.Task;

import java.util.List;

public interface TaskService {

    Task saveTask(String operationId,TaskSaveUpdateRequest taskSaveRequest);
    Task updateTask(String id, TaskSaveUpdateRequest taskUpdateRequest);

    void deleteTask(String id);

    List<Task> getAllTasks();
}
