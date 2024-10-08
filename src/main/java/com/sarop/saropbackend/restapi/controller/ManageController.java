package com.sarop.saropbackend.restapi.controller;

import com.sarop.saropbackend.restapi.dto.LayerGroupRequest;
import com.sarop.saropbackend.restapi.dto.WorkspaceRequest;
import com.sarop.saropbackend.restapi.service.ManageService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;


@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManageController {

    private final ManageService manageService;


    @GetMapping("/workspaces")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> getWorkspaces(@RequestParam(required = false) Optional<String> workspaceName) {

        return ResponseEntity.ok(manageService.getWorkSpaces(workspaceName));
    }


    @PostMapping("/workspaces")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> postWorkspace(@RequestBody WorkspaceRequest request) {

        manageService.postWorkspace(request.getWorkspaceName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/workspaces/{workspaceName}")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> deleteWorkSpace(@PathVariable("workspaceName") String workSpaceName) {
        manageService.deleteWorkSpace(workSpaceName);
        return ResponseEntity.ok().build();

    }
    @GetMapping("/layers")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> getLayers(){
        return ResponseEntity.ok(manageService.getLayers());
    }
    @GetMapping("/workspaces/{workspaceName}/layers")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','OPERATION_ADMIN')")
    public ResponseEntity<?> getLayer(@PathVariable("workspaceName") String workSpaceName,
                                      @RequestParam(required = false)Optional<String> mapName
                                      ) {

        return ResponseEntity.ok(manageService.getLayersByWorkspaces(workSpaceName,mapName));
    }
    @DeleteMapping("/workspaces/{workspaceName}/layers/{layerName}")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> deleteLayer(@PathVariable("workspaceName") String workSpaceName,@PathVariable("layerName") String layerName) {
        manageService.deleteLayer(workSpaceName,layerName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/workspaces/{workspaceName}/coveragestores")
    @PreAuthorize("hasAnyAuthority('ADMIN','OPERATION_ADMIN')")
    public ResponseEntity<?> postCoverageStore(@PathVariable("workspaceName")String workspaceName, @RequestParam("layerName") String layerName,
                                               @RequestParam("description") String mapDescription,
                                               @RequestParam("file") MultipartFile file) {
        manageService.postCoverageStore(workspaceName,layerName,mapDescription,file);
        return ResponseEntity.ok().build();

    }
    /*
    @PostMapping("/layerGroup")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> postLayerGroup(@RequestBody LayerGroupRequest layerGroupRequest) throws Exception {
        return ResponseEntity.ok(manageService.createLayerGroup(layerGroupRequest));
    }
    */



}