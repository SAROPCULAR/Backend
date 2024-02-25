package com.sarop.saropbackend.operation.dto;

import com.sarop.saropbackend.operation.model.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationUpdateRequest {
    private String id;
    private String opName;
    private OperationStatus status;
}