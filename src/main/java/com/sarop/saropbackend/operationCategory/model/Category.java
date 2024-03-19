package com.sarop.saropbackend.operationCategory.model;

import com.sarop.saropbackend.operation.model.Operation;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String categoryName;

    @OneToMany
    private List<Operation> operationList;
}
