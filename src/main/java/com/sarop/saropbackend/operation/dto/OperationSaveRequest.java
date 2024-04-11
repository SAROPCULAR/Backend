package com.sarop.saropbackend.operation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationSaveRequest {

    private int operationNumber;
    private String operationDate;
    private String name;
    private String categoryName;
    private String teamName;
    private List<String> maps;
}
