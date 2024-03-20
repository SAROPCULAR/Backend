package com.sarop.saropbackend.restapi.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8080/geoserver/rest/workspaces/" + workSpaceName;
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        if (response.getStatusCode().is2xxSuccessful()) {

            System.out.println("Workspace başarıyla silindi: " + workSpaceName);
        } else {

            System.err.println("Workspace silinirken hata oluştu: " + response.getStatusCode());
        }
    }

    public List<String> getLayersByWorkspaces(String workSpaceName) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setBasicAuth(username, password);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8080/geoserver/rest/workspaces/" + workSpaceName + "/layers";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        List<String> resultList = new ArrayList<>();
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            try {
                // Assuming Jackson ObjectMapper is available in the classpath
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                JsonNode layersNode = jsonNode.path("layers").path("layer");
                if (layersNode.isArray()) {
                    ArrayNode layerArrayNode = (ArrayNode) layersNode;
                    for (JsonNode item : layerArrayNode) {
                        String name = item.path("name").asText();
                        resultList.add(name);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCode());
        }

        return resultList;
    }

    public void deleteLayer(String workSpaceName, String layerName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8080/geoserver/rest/workspaces/" + workSpaceName + "/layers/" + layerName;
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        if (response.getStatusCode().is2xxSuccessful()) {

            System.out.println("Layer başarıyla silindi: " + layerName);
        } else {

            System.err.println("Layer silinirken hata oluştu: " + response.getStatusCode());
        }
    }

    public void postCoverageStore(String workspaceName, String store, String method, String format, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);


        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                geoserverUrl + "/workspaces/" + workspaceName + "/coveragestores/" + store + "/" + method + "." + format,
                HttpMethod.POST,
                entity,
                String.class);

        System.out.println("Response: " + response.getBody());
    }

    public   ResponseEntity<String> getCoverageStore(String workspaceName, String store) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                geoserverUrl + "/workspaces/" + workspaceName + "/coveragestores/" + store,
                HttpMethod.GET,
                entity,
                String.class);
        return response;


    }
    public   ResponseEntity<String> getCoverageStores(String workspaceName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                geoserverUrl + "/workspaces/" + workspaceName + "/coveragestores" ,
                HttpMethod.GET,
                entity,
                String.class);
        return response;


    }
    public void postCoverageStore1(String workspaceName, String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);

        HttpEntity<String> entity = new HttpEntity<>(requestBody,headers);

        ResponseEntity<String> response = restTemplate.exchange(
                geoserverUrl + "/workspaces/" + workspaceName + "/coveragestores/",
                HttpMethod.POST,
                entity,
                String.class);

        System.out.println("Response: " + response.getBody());
    }
}