package com.sarop.saropbackend.restapi.controller;

import com.sarop.saropbackend.restapi.service.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public List<String> getLayer(@PathVariable("workspaceName") String workSpaceName) {

        return manageService.getLayersByWorkspaces(workSpaceName);
    }
    @DeleteMapping("/workspaces/{workspaceName}/layers/{layerName}")
    public void deleteLayer(@PathVariable("workspaceName") String workSpaceName,@PathVariable("layerName") String layerName) {
        manageService.deleteLayer(workSpaceName,layerName);


    }
    @PostMapping("/workspaces/{workspaceName}/coveragestores/{store}/{method}.{format}")
    public void postCoverageStore(@PathVariable("workspaceName")String workspaceName, @PathVariable("store") String store, @PathVariable("method") String method,@PathVariable("format") String format,  @RequestBody  String url ) {
        manageService.postCoverageStore(workspaceName,store,method,format,url);

    }
    @GetMapping("/workspaces/{workspaceName}/coveragestores/{store}")
    public   ResponseEntity<String> getCoverageStore(@PathVariable("workspaceName") String workSpaceName, @PathVariable("store") String store) {

        return manageService.getCoverageStore(workSpaceName,store);
    }

    @GetMapping("/workspaces/{workspaceName}/coveragestores")
    public   ResponseEntity<String> getCoverageStores(@PathVariable("workspaceName") String workSpaceName) {

        return manageService.getCoverageStores(workSpaceName);

    }
    @PostMapping("/workspaces/{workspaceName}/coveragestores2")
    public void postCoverageStore1(@PathVariable("workspaceName")String workspaceName,@RequestBody String requestBody ) {
        manageService.postCoverageStore1(workspaceName,requestBody);
        System.out.println("dene");
    }


}