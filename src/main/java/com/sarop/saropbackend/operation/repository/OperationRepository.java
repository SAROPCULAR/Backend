package com.sarop.saropbackend.operation.repository;


import com.sarop.saropbackend.operation.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,String> {

}
