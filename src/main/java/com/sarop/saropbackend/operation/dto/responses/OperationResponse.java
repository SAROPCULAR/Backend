package com.sarop.saropbackend.operation.dto.responses;

import com.sarop.saropbackend.category.dto.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationResponse {
    private String id;
    private int operationNumber;
    private String operationDate;
    private String name;
    private CategoryResponse category;
    private OperationTeamResponse team;
    private List<OperationMapResponse> maps;
}
