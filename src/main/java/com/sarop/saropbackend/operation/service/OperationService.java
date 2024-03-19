package com.sarop.saropbackend.operation.service;

import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.operation.dto.OperationUpdateRequest;
import com.sarop.saropbackend.operation.model.Operation;

import java.util.List;


public interface OperationService {

     Operation saveOperation(OperationSaveRequest operationSaveRequest);

     Operation updateOperation(String id, OperationUpdateRequest operationUpdateRequest);

     List<Operation> getAllOperation();

     void deleteOperation(String id);
}
