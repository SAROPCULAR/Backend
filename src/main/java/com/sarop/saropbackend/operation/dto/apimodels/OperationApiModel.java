package com.sarop.saropbackend.operation.dto.apimodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationApiModel {
    @JsonProperty("OperationId")
    private String operationId;

    @JsonProperty("OperationNumber")
    private int operationNumber;

    @JsonProperty("OperationDate")
    private String operationDate;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("CategoryName")
    private String categoryName;
}
