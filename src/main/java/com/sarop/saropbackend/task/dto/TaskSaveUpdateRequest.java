package com.sarop.saropbackend.task.dto;

import com.sarop.saropbackend.task.model.TaskStatus;
import com.sarop.saropbackend.user.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskSaveUpdateRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private Date dueDate;

    @NotNull
    private TaskStatus taskStatus;

    @NotNull
    private List<User> responsiblePeople;
}
