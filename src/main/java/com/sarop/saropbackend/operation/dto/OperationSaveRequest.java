package com.sarop.saropbackend.operation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationSaveRequest {

    @NotNull
    private String id;

    @NotNull
    private String opName;


}