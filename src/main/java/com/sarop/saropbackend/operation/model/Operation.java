package com.sarop.saropbackend.operation.model;

import com.sarop.saropbackend.operationCategory.model.Category;
import com.sarop.saropbackend.task.model.Task;
import com.sarop.saropbackend.team.model.Team;
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
@Table(name = "operation")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private int opNumber;

    @Column
    private Date operationDate;

    @Column
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne
    private Team operationalTeam;

    @OneToMany(mappedBy = "operation")
    private List<Task> tasks;
}
