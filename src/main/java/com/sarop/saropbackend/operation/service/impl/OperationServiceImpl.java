package com.sarop.saropbackend.operation.service.impl;

import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.operation.dto.OperationUpdateRequest;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operation.model.OperationStatus;
import com.sarop.saropbackend.operation.repository.OperationRepository;
import com.sarop.saropbackend.operation.service.OperationService;
import com.sarop.saropbackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    @Override
    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    public Operation getOperationById(String id) {
        return operationRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Operation> getOperationsByUser(User user) {
        return operationRepository.findByResponsiblesContaining(user);
    }

    @Override
    public Operation saveOperation(OperationSaveRequest operationSaveRequest) {
        var operation = Operation.builder()
                .opName(operationSaveRequest.getOpName())
                // Diğer alanları da aynı şekilde ekle
                .build();
        return operationRepository.save(operation);
    }

    @Override
    public void deleteOperation(String id) {
        operationRepository.deleteById(id);
    }

    @Override
    public Operation updateOperation(OperationUpdateRequest operationUpdateRequest, String id) {
        Operation operation = operationRepository.findById(id).orElseThrow();
        if (operationUpdateRequest.getOpName() != null) {
            operation.setOpName(operationUpdateRequest.getOpName());
        }
        if (operationUpdateRequest.getStatus() != null) {
            operation.setStatus(operationUpdateRequest.getStatus());
        }
        return operationRepository.save(operation);
    }

    @Override
    public List<Operation> getOperationsByStatus(OperationStatus status) {
        return operationRepository.findByStatus(status);
    }
}