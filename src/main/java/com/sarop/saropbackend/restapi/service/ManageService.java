package com.sarop.saropbackend.restapi.service;

import com.sarop.saropbackend.restapi.dto.LayerGroupRequest;
import com.sarop.saropbackend.restapi.dto.Responses.MapResponse;
import com.sarop.saropbackend.restapi.dto.Responses.WorkspaceResponse;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.restapi.entity.Workspace;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ManageService {
    List<WorkspaceResponse> getWorkSpaces(Optional<String> workspaceName);
    void postWorkspace(String workspaceName);
    void deleteWorkSpace(String workSpaceName);
    List<MapResponse> getLayersByWorkspaces(String workSpaceName, Optional<String> mapName);

    // TO DO: Update workspace, update layer, workspace'den map silmek ve başka workspace'e eklemek eklenecek

    void deleteLayer(String workSpaceName, String layerName);


    void postCoverageStore(String workspaceName, String layerName, String mapDescription, MultipartFile file);

    //String createLayerGroup(LayerGroupRequest layerGroupRequest) throws Exception;
}
