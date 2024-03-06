package com.sarop.saropbackend.restapi.controller;

import com.sarop.saropbackend.restapi.service.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManageController {

    private final ManageService manageService;


    @GetMapping("/workspaces")
    public List<String> getWorkspaces() {

        return manageService.getWorkSpaces();
    }


    @PostMapping("/workspaces")
    public void postWorkspace(@RequestBody String workSpaceName) {
        manageService.postWorkspace(workSpaceName);

    }
    @DeleteMapping("/workspaces/{workspaceName}")
    public void deleteWorkSpace(@PathVariable("workspaceName") String workSpaceName) {
        manageService.deleteWorkSpace(workSpaceName);


    }
    @GetMapping("/workspaces/{workspaceName}/layers")
    public List<String> getWorkspaces(@PathVariable("workspaceName") String workSpaceName) {

        return manageService.getLayersByWorkspaces(workSpaceName);
    }
    @DeleteMapping("/workspaces/{workspaceName}/layers/{layerName}")
    public void deleteWorkSpace(@PathVariable("workspaceName") String workSpaceName,@PathVariable("layerName") String layerName) {
        manageService.deleteLayer(workSpaceName,layerName);


    }

}