package com.sarop.saropbackend.operationCategory.dto;


import com.sarop.saropbackend.operation.model.Operation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateRequest {
    @NotEmpty
    private String categoryName;

    @NotNull
    private List<Operation> operationList;
}
