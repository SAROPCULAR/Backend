package com.sarop.saropbackend.task.model;

import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column
    private String description;
    @Column
    private Date dueDate;

    @Column
    private TaskStatus taskStatus;

    @ManyToMany
    private List<User> responsiblePeople;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id")
    private Operation operation;
}
