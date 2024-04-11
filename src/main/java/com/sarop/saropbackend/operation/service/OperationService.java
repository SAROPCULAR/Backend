package com.sarop.saropbackend.operation.service;

import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.operation.model.Operation;

import java.util.List;

public interface OperationService {

     Operation addOperation(OperationSaveRequest operationSaveRequest);

     Operation updateOperation(String id,OperationSaveRequest operationUpdateRequest);

     List<Operation> getAllOperations();

     void deleteOperation(String id);

}
