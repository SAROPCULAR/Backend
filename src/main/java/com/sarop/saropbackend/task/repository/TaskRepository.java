package com.sarop.saropbackend.task.repository;

import com.sarop.saropbackend.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,String> {
}
