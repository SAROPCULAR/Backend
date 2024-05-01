package com.sarop.saropbackend.restapi.service;


import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.note.model.Note;
import com.sarop.saropbackend.note.repository.NoteRepository;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operation.repository.OperationRepository;
import com.sarop.saropbackend.polygon.model.Polygon;
import com.sarop.saropbackend.polygon.repository.PolygonRepository;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.restapi.entity.Workspace;
import com.sarop.saropbackend.restapi.repository.MapRepository;
import com.sarop.saropbackend.restapi.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ManageServiceImpl implements ManageService {


    private final RestTemplate restTemplate;

    @Value("${geoserver.url}")
    private final String geoserverUrl;

    @Value("${geoserver.username}")
    private final String username;

    @Value("${geoserver.password}")
    private final String password;

    private final HttpHeaders headers;

    private final MapRepository mapRepository;

    private final WorkspaceRepository workspaceRepository;

    private final NoteRepository noteRepository;

    private final PolygonRepository polygonRepository;
    private final OperationRepository operationRepository;
    public ManageServiceImpl(RestTemplate restTemplate,
                             MapRepository mapRepository,
                             WorkspaceRepository workspaceRepository,
                             @Value("${geoserver.url}") String geoserverUrl,
                             @Value("${geoserver.username}") String username,
                             @Value("${geoserver.password}") String password, NoteRepository noteRepository, PolygonRepository polygonRepository, OperationRepository operationRepository) {
        this.restTemplate = restTemplate;
        this.mapRepository = mapRepository;
        this.workspaceRepository = workspaceRepository;
        this.geoserverUrl = geoserverUrl;
        this.username = username;
        this.password = password;
        this.noteRepository = noteRepository;
        this.polygonRepository = polygonRepository;
        this.operationRepository = operationRepository;
        headers = new HttpHeaders();
        headers.setBasicAuth(this.username, this.password);
    }

    public List<Workspace> getWorkSpaces(Optional<String> workspaceName) {
        List<Workspace> workspaces = workspaceRepository.findAll().stream()
                .filter(workspace ->
                        (workspaceName.isEmpty() || workspace.getName().equals(workspaceName.get()))
                )
                .collect(Collectors.toList());
        return workspaces;
    }

    public void postWorkspace(String workspaceName) {

        headers.setContentType(MediaType.APPLICATION_JSON);


        String requestBody = "{\"workspace\": {\"name\": \"" + workspaceName + "\"}}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        restTemplate.exchange(
                geoserverUrl + "/workspaces",
                HttpMethod.POST,
                entity,
                String.class);


        Workspace workspace = Workspace.builder().id(Util.generateUUID()).name(workspaceName).build();
        workspaceRepository.save(workspace);

    }




    public void deleteWorkSpace(String workSpaceName) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8080/geoserver/rest/workspaces/" + workSpaceName;
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        workspaceRepository.deleteByName(workSpaceName);
    }

    public List<Map> getLayersByWorkspaces(String workSpaceName, Optional<String> mapName) {
        List<Map> maps = mapRepository.findAllByWorkspaceName(workSpaceName).stream()
                .filter(map ->
                        (mapName.isEmpty() || map.getMapName().equals(mapName.get()))
                )
                .collect(Collectors.toList());
        return maps;
    }


    public void deleteLayer(String workSpaceName, String layerName) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8080/geoserver/rest/workspaces/" + workSpaceName + "/layers/" + layerName;
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        Workspace workspace = workspaceRepository.findWorkspaceByName(workSpaceName).orElseThrow();
        Map map = mapRepository.findMapByMapNameAndAndWorkspace(layerName,workspace);
        if(map.getNotes() != null){
            for(Note note : map.getNotes()){
                noteRepository.delete(note);
            }
        }
        if(map.getPolygons() != null){
            for(Polygon polygon: map.getPolygons()){
                polygonRepository.delete(polygon);
            }
        }
        if(map.getOperations() != null){
            for(Operation operation : map.getOperations()){
                operation.getMaps().remove(map);
                operationRepository.save(operation);
            }
        }

        mapRepository.delete(map);

    }

    private String GdalConvert(String ecwPath){
        try {
            String outputPath = "C:/Users/cikla/OneDrive/Masaüstü/output.tif";
            // Command to convert ECW to TIFF
            String[] command = {"gdal_translate", "-of", "GTiff", ecwPath, outputPath};

            // Create ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // Redirect error stream to output stream
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Get the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);
            return "file:///" + outputPath;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String saveFileToLocal(MultipartFile file, String layerName) throws IOException {
        String directory = "C:/Users/cikla/OneDrive/Masaüstü/DERSLER/23-24 Spring Term/Bitirme/src/main/resources/maps"; // Change this to your directory path
        String fileName = layerName + "." + getFileExtension(file.getOriginalFilename());
        String filePath = directory + "/" + fileName;
        File localFile = new File(filePath);
        file.transferTo(localFile);
        return filePath;
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }

    private String determineMapType(String filePath) {
        String mapType = "";
        String extension = getFileExtension(filePath).toLowerCase();
        if (extension.equals("tif") || extension.equals("tiff")) {
            mapType = "GeoTIFF";
        } else if (extension.equals("ecw")) {
            mapType = "ECW";
        }
        return mapType;
    }

    public void postCoverageStore(String workspaceName, String layerName, String description, MultipartFile file) {
        try {
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Save the file to local storage
            String localFilePath = saveFileToLocal(file, layerName);

            // Determine map type
            String mapType = determineMapType(localFilePath);

            String displayUrl = "http://localhost:8080/geoserver/" +workspaceName + "/wms";

            // Include workspace in the JSON payload
            String requestBody = "{\n" +
                    "  \"coverageStore\": {\n" +
                    "    \"name\": \"" + layerName + "\",\n" +
                    "    \"enabled\": true,\n" +
                    "    \"type\": \"" + mapType + "\",\n" +
                    "    \"workspace\": \"" + workspaceName + "\",\n" +
                    "    \"url\": \"" + localFilePath + "\"\n" +
                    "  }\n" +
                    "}";

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            String apiUrl = geoserverUrl + "/workspaces/" + workspaceName + "/coveragestores";

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            // Add coverage
            String nativeName = localFilePath.substring(localFilePath.lastIndexOf("/") + 1,localFilePath.lastIndexOf("."));
            String coverageJson = "{" +
                    "\"coverage\": {" +
                    "\"nativeName\": \"" + nativeName + "\"," +
                    "\"nativeCoverageName\": \"" + nativeName + "\"," +
                    "\"enabled\": true," +
                    "\"metadata\": \"\"," +
                    "\"keywords\": \"\"," +
                    "\"metadataLinks\": \"\"," +
                    "\"name\": \"" + layerName + "\"," +
                    "\"title\": \"" + layerName + "\"," +
                    "\"srs\": \"EPSG:4326\"," +
                    "\"projectionPolicy\": \"REPROJECT_TO_DECLARED\"" +
                    "}" +
                    "}";
            addCoverage(workspaceName, layerName, coverageJson);

            var workspace = workspaceRepository.findWorkspaceByName(workspaceName).orElseThrow();
            Map map = Map.builder().id(Util.generateUUID()).mapName(layerName)
                    .fileUrl(localFilePath).mapType(mapType).mapDescription(description).workspace(workspace).displayUrl(displayUrl).build();
            mapRepository.save(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addCoverage(String workspace, String coverageStore, String coverageJson) {
        try {
            headers.setContentType(MediaType.APPLICATION_JSON);

            String apiUrl = geoserverUrl + "/workspaces/" + workspace + "/coveragestores/" + coverageStore + "/coverages";

            HttpEntity<String> requestEntity = new HttpEntity<>(coverageJson, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(apiUrl, requestEntity, String.class);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    }
