package com.sarop.saropbackend.operation.mapper;

import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.operation.dto.OperationUpdateRequest;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operationCategory.model.Category;
import org.springframework.stereotype.Component;


@Component
public class OperationMapper {

    public Operation operationSaveRequestToOperation(OperationSaveRequest operationSaveRequest){
        var operation = Operation.builder().name(operationSaveRequest.getName()).opNumber(operationSaveRequest.getOpNumber())
                .operationDate(operationSaveRequest.getOperationDate()).category(new Category())
                .operationalTeam(operationSaveRequest.getOperationalTeam())
                .build();
        return operation;
    }
    public Operation operationUpdateRequestToOperation(Operation operation, OperationUpdateRequest operationUpdateRequest){
        operation.setName(operationUpdateRequest.getName());
        operation.setOperationDate(operationUpdateRequest.getOperationDate());
        operation.setOperationalTeam(operationUpdateRequest.getOperationalTeam());
        operation.setOpNumber(operationUpdateRequest.getOpNumber());
        return operation;
    }
}
