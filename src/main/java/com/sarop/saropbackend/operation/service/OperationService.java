package com.sarop.saropbackend.operation.service;

import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.operation.model.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationService {

     Operation addOperation(OperationSaveRequest operationSaveRequest);

     Operation updateOperation(String id,OperationSaveRequest operationUpdateRequest);

     List<Operation> getAllOperations(Optional<Integer> operationNumber, Optional<String> operationDate,
                                      Optional<String> name, Optional<String> categoryName, Optional<String> teamName);

     void deleteOperation(String id);

}
