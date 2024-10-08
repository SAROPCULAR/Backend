package com.sarop.saropbackend.restapi.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sarop.saropbackend.common.Coordinate;
import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.note.model.Note;
import com.sarop.saropbackend.note.repository.NoteRepository;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operation.repository.OperationRepository;
import com.sarop.saropbackend.polygon.model.Polygon;
import com.sarop.saropbackend.polygon.repository.PolygonRepository;
import com.sarop.saropbackend.restapi.dto.LayerGroupRequest;
import com.sarop.saropbackend.restapi.dto.Responses.*;
import com.sarop.saropbackend.restapi.dto.WorkspaceRequest;
import com.sarop.saropbackend.restapi.entity.LayerGroup;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.restapi.entity.Workspace;
import com.sarop.saropbackend.restapi.repository.MapRepository;
import com.sarop.saropbackend.restapi.repository.WorkspaceRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.json.JSONObject;

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

    public List<WorkspaceResponse> getWorkSpaces(Optional<String> workspaceName) {
        List<WorkspaceResponse> workspaceResponses = new ArrayList<>();
        List<Workspace> workspaces = workspaceRepository.findAll().stream()
                .filter(workspace ->
                        (workspaceName.isEmpty() || workspace.getName().equals(workspaceName.get()))
                )
                .collect(Collectors.toList());
        for(Workspace workspace : workspaces){
            var workspaceResponse = WorkspaceResponse.builder().id(workspace.getId()).name(workspace.getName()).maps(new ArrayList<>()).build();
            if(workspace.getMaps() != null && !workspace.getMaps().isEmpty()){
                for(Map map : workspace.getMaps()){
                    var mapResponse = WorkspaceMapResponse.builder().id(map.getId()).mapName(map.getMapName()).mapDescription(map.getMapDescription())
                            .mapType(map.getMapType()).displayUrl(map.getDisplayUrl()).build();
                    workspaceResponse.getMaps().add(mapResponse);
                }
            }
            workspaceResponses.add(workspaceResponse);
        }
        return workspaceResponses;
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


        Workspace workspace = Workspace.builder().id(Util.generateUUID()).name(workspaceName).maps(new ArrayList<>()).build();
        workspaceRepository.save(workspace);

    }



   @Transactional
    public void deleteWorkSpace(String workSpaceName) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8080/geoserver/rest/workspaces/" + workSpaceName + "?recurse=true";
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        Workspace workspace = workspaceRepository.findWorkspaceByName(workSpaceName);
        for(Map map : workspace.getMaps()){
            mapRepository.delete(map);
        }
        workspaceRepository.deleteByName(workSpaceName);
    }
    public List<MapResponse> getLayers(){
        List<MapResponse> mapResponses = new ArrayList<>();
        List<Map> maps = mapRepository.findAll();
        for(Map map : maps){
            MapResponse mapResponse = MapResponse.builder().id(map.getId()).mapName(map.getMapName())
                    .mapType(map.getMapType()).mapDescription(map.getMapDescription())
                    .displayUrl(map.getDisplayUrl()).notes(new ArrayList<>()).operations(new ArrayList<>())
                    .polygons(new ArrayList<>()).workspace(new MapWorkspaceResponse()).build();
            MapWorkspaceResponse mapWorkspaceResponse = new MapWorkspaceResponse();
            mapWorkspaceResponse.setId(map.getWorkspace().getId());
            mapWorkspaceResponse.setName(map.getWorkspace().getName());
            mapResponse.setWorkspace(mapWorkspaceResponse);
            if(map.getOperations() != null && !map.getOperations().isEmpty()){
                for(Operation operation : map.getOperations()){
                    MapOperationResponse mapOperationResponse = new MapOperationResponse();
                    mapOperationResponse.setId(operation.getId());
                    mapOperationResponse.setName(operation.getName());
                }
            }
            if(map.getPolygons() != null && !map.getPolygons().isEmpty()){
                for(Polygon polygon : map.getPolygons()){
                    MapPolygonResponse mapPolygonResponse = new MapPolygonResponse();
                    mapPolygonResponse.setId(polygon.getId());
                    mapPolygonResponse.setCoordinates(new ArrayList<>());
                    for(Coordinate coordinate : polygon.getCoordinates()){
                        CoordinateResponse coordinateResponse = new CoordinateResponse();
                        coordinateResponse.setId(coordinate.getId());
                        coordinateResponse.setX(coordinate.getX());
                        coordinateResponse.setY(coordinate.getY());
                        mapPolygonResponse.getCoordinates().add(coordinateResponse);
                    }
                    mapResponse.getPolygons().add(mapPolygonResponse);
                }
            }
            if(map.getNotes() != null && !map.getNotes().isEmpty()){
                for(Note note: map.getNotes()){
                    MapNoteResponse mapNoteResponse = new MapNoteResponse();
                    mapNoteResponse.setId(note.getId());
                    CoordinateResponse coordinateResponse = new CoordinateResponse();
                    coordinateResponse.setId(note.getCoordinate().getId());
                    coordinateResponse.setX(note.getCoordinate().getX());
                    coordinateResponse.setY(note.getCoordinate().getY());
                    mapNoteResponse.setCoordinate(coordinateResponse);
                    mapResponse.getNotes().add(mapNoteResponse);
                }

            }
            mapResponses.add(mapResponse);

        }
        return mapResponses;
    }
    public List<MapResponse> getLayersByWorkspaces(String workSpaceName, Optional<String> mapName) {
        List<MapResponse> mapResponses = new ArrayList<>();
        List<Map> maps = mapRepository.findAllByWorkspaceName(workSpaceName).stream()
                .filter(map ->
                        (mapName.isEmpty() || map.getMapName().equals(mapName.get()))
                )
                .collect(Collectors.toList());
        for(Map map : maps){
            MapResponse mapResponse = MapResponse.builder().id(map.getId()).mapName(map.getMapName())
                    .mapType(map.getMapType()).mapDescription(map.getMapDescription())
                    .displayUrl(map.getDisplayUrl()).notes(new ArrayList<>()).operations(new ArrayList<>())
                    .polygons(new ArrayList<>()).workspace(new MapWorkspaceResponse()).build();
            MapWorkspaceResponse mapWorkspaceResponse = new MapWorkspaceResponse();
            mapWorkspaceResponse.setId(map.getWorkspace().getId());
            mapWorkspaceResponse.setName(map.getWorkspace().getName());
            mapResponse.setWorkspace(mapWorkspaceResponse);
            if(map.getOperations() != null && !map.getOperations().isEmpty()){
                for(Operation operation : map.getOperations()){
                    MapOperationResponse mapOperationResponse = new MapOperationResponse();
                    mapOperationResponse.setId(operation.getId());
                    mapOperationResponse.setName(operation.getName());
                }
            }
            if(map.getPolygons() != null && !map.getPolygons().isEmpty()){
                for(Polygon polygon : map.getPolygons()){
                    MapPolygonResponse mapPolygonResponse = new MapPolygonResponse();
                    mapPolygonResponse.setId(polygon.getId());
                    mapPolygonResponse.setCoordinates(new ArrayList<>());
                    for(Coordinate coordinate : polygon.getCoordinates()){
                        CoordinateResponse coordinateResponse = new CoordinateResponse();
                        coordinateResponse.setId(coordinate.getId());
                        coordinateResponse.setX(coordinate.getX());
                        coordinateResponse.setY(coordinate.getY());
                        mapPolygonResponse.getCoordinates().add(coordinateResponse);
                    }
                    mapResponse.getPolygons().add(mapPolygonResponse);
                }
            }
            if(map.getNotes() != null && !map.getNotes().isEmpty()){
                for(Note note: map.getNotes()){
                    MapNoteResponse mapNoteResponse = new MapNoteResponse();
                    mapNoteResponse.setId(note.getId());
                    CoordinateResponse coordinateResponse = new CoordinateResponse();
                    coordinateResponse.setId(note.getCoordinate().getId());
                    coordinateResponse.setX(note.getCoordinate().getX());
                    coordinateResponse.setY(note.getCoordinate().getY());
                    mapNoteResponse.setCoordinate(coordinateResponse);
                    mapResponse.getNotes().add(mapNoteResponse);
                }

            }
            mapResponses.add(mapResponse);

        }
        return mapResponses;
    }

    @Transactional
    public void deleteLayer(String workSpaceName, String layerName) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8080/geoserver/rest/workspaces/" + workSpaceName + "/layers/" + layerName;

        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        Workspace workspace = workspaceRepository.findWorkspaceByName(workSpaceName);
        Map map = mapRepository.findMapByMapNameAndAndWorkspace(layerName,workspace);
        workspace.getMaps().remove(map);
        if(map.getNotes() != null){
            for(Note note : map.getNotes()){
                note.setMap(null);
                noteRepository.delete(note);
            }
        }
        if(map.getPolygons() != null){
            for(Polygon polygon: map.getPolygons()){
                polygon.setMap(null);
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
        } else if (extension.equals("jpg") || extension.equals("jpeg")) {
            mapType = "WorldImage";
        }else if(extension.equals("ecw")){
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
                    "\"projectionPolicy\": \"REPROJECT_TO_DECLARED\"" +
                    "}" +
                    "}";
            addCoverage(workspaceName, layerName, coverageJson);
            // http://localhost:8080/geoserver/tiger/wms?service=WMS&version=1.1.0&request=GetMap&layers=tiger%3Atiger_roads&bbox=-74.02722%2C40.684221%2C-73.907005%2C40.878178&width=476&height=768&srs=EPSG%3A4326&styles=&format=application/openlayers
            String displayUrl = getDisplayUrl(workspaceName,layerName,layerName);
            var workspace = workspaceRepository.findWorkspaceByName(workspaceName);
            Map map = Map.builder().id(Util.generateUUID()).mapName(layerName)
                    .fileUrl(localFilePath).mapType(mapType).mapDescription(description).workspace(workspace).displayUrl(displayUrl).build();
            mapRepository.save(map);
            workspace.getMaps().add(map);
            workspaceRepository.save(workspace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getDisplayUrl(String workspaceName, String coverageStoreName, String layerName) {
    String url = "http://localhost:8080/geoserver/rest/workspaces/" + workspaceName + "/coveragestores/" + coverageStoreName
            + "/coverages/" + layerName + ".json";
    HttpClient client = HttpClient.newHttpClient();

    // Encoding username and password for basic auth
    String auth = username + ":" + password;
    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    String authHeader = "Basic " + encodedAuth;

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Accept", "application/json")
            .header("Authorization", authHeader)  // Add the authorization header
            .build();

    try {
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String jsonString = response.body();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject coverage = jsonObject.getJSONObject("coverage");
        JSONObject bbox = coverage.getJSONObject("nativeBoundingBox");
        double minx = bbox.getDouble("minx");
        double maxx = bbox.getDouble("maxx");
        double miny = bbox.getDouble("miny");
        double maxy = bbox.getDouble("maxy");
        String srs;
        if (bbox.get("crs") instanceof JSONObject) {
            srs = bbox.getJSONObject("crs").getString("$").replace(":", "%3A");
        } else {
            srs = bbox.getString("crs").replace(":", "%3A");
        }
        //http://localhost:8080/geoserver/MyWorkspace19/wms?service=WMS&version=1.1.0&request=GetMap&layers=MyWorkspace19%3AECWJPEG&bbox=-0.5%2C-0.5%2C4805.5%2C4805.5&width=768&height=768&srs=EPSG%3A404000&styles=&format=application/openlayers
        // Construct WMS display URL
        String displayUrl = "http://localhost:8080/geoserver/" +workspaceName+"/wms?service=WMS&version=1.1.0&request=GetMap&layers=" + workspaceName+"%3A" + layerName+"&bbox="+
        minx+"%2C"+miny+"%2C"+maxx+"%2C"+maxy+"&width=476&height=768&srs="+srs+"&styles=&format=image/jpeg";



        return displayUrl;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "";
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
    /*
    @Override
    public String createLayerGroup(LayerGroupRequest layerGroupRequest) throws Exception {
        String layerGroupJson = buildLayerGroupJson(layerGroupRequest);
        String url = geoserverUrl + "/layergroups";


        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(layerGroupJson, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url,  request, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            List<Map> layers = new ArrayList<>();
            LayerGroup layerGroup = LayerGroup.builder().id(Util.generateUUID()).name(layerGroupRequest.getName())
                    .title(layerGroupRequest.getTitle()).abstractText(layerGroupRequest.getAbstractText())
                    .mode(layerGroupRequest.getMode()).workspace(workspaceRepository.findWorkspaceByName(layerGroupRequest.getWorkspace()))
                    .build();
            for(String layerName : layerGroupRequest.getLayers()){
                Map layer = mapRepository.findMapByMapName(layerName);
                layers.add(layer);
            }
            layerGroup.setLayers(layers);
            return "Layer group created successfully!";
        } else {
            return "Failed to create layer group: " + response.getBody();
        }
    }

    private String buildLayerGroupJson(LayerGroupRequest layerGroupRequest) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        rootNode.put("name", layerGroupRequest.getName());
        rootNode.put("mode", layerGroupRequest.getMode().toString());
        rootNode.put("title", layerGroupRequest.getTitle());
        rootNode.put("abstractTxt", layerGroupRequest.getAbstractText());

        if (layerGroupRequest.getWorkspace() != null && !layerGroupRequest.getWorkspace().isEmpty()) {
            if(workspaceRepository.findWorkspaceByName(layerGroupRequest.getWorkspace()) == null){
                postWorkspace(layerGroupRequest.getWorkspace());
            }
            ObjectNode workspaceNode = rootNode.putObject("workspace");
            workspaceNode.put("name", layerGroupRequest.getWorkspace());
        }

        ArrayNode publishedArray = rootNode.putObject("publishables").putArray("published");
        for (int i = 0; i < layerGroupRequest.getLayers().size(); i++) {
            if(mapRepository.findMapByMapName(layerGroupRequest.getLayers().get(i)) == null){
                throw new Exception("Layer does not exist");
            }
            ObjectNode layerNode = publishedArray.addObject();
            layerNode.put("name", layerGroupRequest.getLayers().get(i));
        }
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        return jsonString;
    }
    */

    }
