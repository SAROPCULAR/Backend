package com.sarop.saropbackend.operation.dto.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sarop.saropbackend.operation.dto.apimodels.OperationApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationListApiResponse {
    @JsonProperty("StartDateTime")
    private String StartDateTime;

    @JsonProperty("OperationListApiModels")
    private List<OperationApiModel> OperationListApiModels;
}
