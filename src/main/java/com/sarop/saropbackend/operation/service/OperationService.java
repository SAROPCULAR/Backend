package com.sarop.saropbackend.operation.service;

import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.operation.dto.OperationUpdateRequest;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operation.model.OperationStatus;
import com.sarop.saropbackend.user.model.User;

import java.util.List;

public interface OperationService {

    List<Operation> getAllOperations();

    Operation getOperationById(String id);

    List<Operation> getOperationsByUser(User user);

    Operation saveOperation(OperationSaveRequest operationSaveRequest);

    void deleteOperation(String id);

    Operation updateOperation(OperationUpdateRequest operationUpdateRequest, String id);

    List<Operation> getOperationsByStatus(OperationStatus status);
}
