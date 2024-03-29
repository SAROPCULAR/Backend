package com.sarop.saropbackend.restapi.service;

import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.restapi.entity.Workspace;

import java.util.List;

public interface ManageService {
    List<Workspace> getWorkSpaces();
    void postWorkspace(String workspaceName);
    void deleteWorkSpace(String workSpaceName);
    List<Map> getLayersByWorkspaces(String workSpaceName);

    // TO DO: Update workspace, update layer, workspace'den map silmek ve başka workspace'e eklemek eklenecek

    void deleteLayer(String workSpaceName, String layerName);


    void postCoverageStore(String workspace,String layerName,String fileUrl);
}
