package com.sarop.saropbackend.restapi.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManageService {


    private final RestTemplate restTemplate;
    private final String geoserverUrl;
    private final String username;
    private final String password;

    public ManageService(RestTemplate restTemplate,
                         @Value("${geoserver.url}") String geoserverUrl,
                         @Value("${geoserver.username}") String username,
                         @Value("${geoserver.password}") String password) {
        this.restTemplate = restTemplate;
        this.geoserverUrl = geoserverUrl;
        this.username = username;
        this.password = password;
    }

    public List<String> getWorkSpaces() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                geoserverUrl + "/workspaces",
                HttpMethod.GET,
                entity,
                String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode workspacesNode = rootNode.get("workspaces").get("workspace");

            List<String> workspaceNames = new ArrayList<>();
            for (JsonNode workspaceNode : workspacesNode) {
                String name = workspaceNode.get("name").asText();
                workspaceNames.add(name);
            }

            return workspaceNames;
        } catch (IOException e) {
            // Handle JSON parsing exception
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public void postWorkspace(String workspaceName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);

        String requestBody = "{\"workspace\": {\"name\": \"" + workspaceName + "\"}}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                geoserverUrl + "/workspaces",
                HttpMethod.POST,
                entity,
                String.class);

        System.out.println("Response: " + response.getBody());
    }


    public void deleteWorkSpace(String workSpaceName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);
        String url = "http://localhost:8080/workspaces/" + workSpaceName;
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        if (response.getStatusCode().is2xxSuccessful()) {

            System.out.println("Workspace başarıyla silindi: " + workSpaceName);
        } else {

            System.err.println("Workspace silinirken hata oluştu: " + response.getStatusCode());
        }
    }
}