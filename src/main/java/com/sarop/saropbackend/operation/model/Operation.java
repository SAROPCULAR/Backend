package com.sarop.saropbackend.operation.model;

import com.sarop.saropbackend.map.Map;
import com.sarop.saropbackend.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="operations")
public class Operation {
    @Id
    @Column(name = "operations_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable=false)
    private String opName;
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private OperationStatus status;
    @ManyToMany
    private List<User> responsibles;
    @Column(nullable = false)
    @ManyToMany
    private List<Map> maps;

}