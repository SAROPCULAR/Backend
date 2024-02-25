package com.sarop.saropbackend.operation.repository;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operation.model.OperationStatus;
import com.sarop.saropbackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface OperationRepository extends JpaRepository<Operation,String> {


    List<Operation> findByResponsiblesContaining(User user);
    List<Operation> findByStatus(OperationStatus status);
}