package com.sarop.saropbackend.operation.service.impl;

import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.operation.dto.OperationUpdateRequest;
import com.sarop.saropbackend.operation.mapper.OperationMapper;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operation.repository.OperationRepository;
import com.sarop.saropbackend.operation.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@RequiredArgsConstructor
@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    @Override
    public Operation saveOperation(OperationSaveRequest operationSaveRequest) {
        var operation = operationMapper.operationSaveRequestToOperation(operationSaveRequest);
        return operationRepository.save(operation);
    }

    @Override
    public Operation updateOperation(String id, OperationUpdateRequest operationUpdateRequest) {
        var operation = operationRepository.findById(id).orElseThrow();
        operation = operationMapper.operationUpdateRequestToOperation(operation,operationUpdateRequest);
        return operationRepository.save(operation);
    }

    @Override
    public List<Operation> getAllOperation() {
        return operationRepository.findAll();
    }

    @Override
    public void deleteOperation(String id) {
        operationRepository.deleteById(id);
    }
}
