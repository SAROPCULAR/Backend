package com.sarop.saropbackend.task.mapper;

import com.sarop.saropbackend.common.Util;

import com.sarop.saropbackend.task.dto.TaskSaveUpdateRequest;
import com.sarop.saropbackend.task.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task TaskSaveRequesttoTask(TaskSaveUpdateRequest taskSaveRequest){
        var task = Task.builder().id(Util.generateUUID()).name(taskSaveRequest.getName())
                .description(taskSaveRequest.getDescription()).dueDate(taskSaveRequest.getDueDate())
                .taskStatus(taskSaveRequest.getTaskStatus()).responsiblePeople(taskSaveRequest.getResponsiblePeople())
                .build();
        return task;
    }
    public Task TaskUpdateRequestToTask(Task task,TaskSaveUpdateRequest taskUpdateRequest){
        task.setName(taskUpdateRequest.getName());
        task.setDescription(taskUpdateRequest.getDescription());
        task.setDueDate(taskUpdateRequest.getDueDate());
        task.setResponsiblePeople(taskUpdateRequest.getResponsiblePeople());
        task.setTaskStatus(taskUpdateRequest.getTaskStatus());
        return task;

    }
}
