package com.sarop.saropbackend.operation.service.impl;

import com.sarop.saropbackend.category.model.Category;
import com.sarop.saropbackend.category.repository.CategoryRepository;
import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.category.dto.CategoryResponse;
import com.sarop.saropbackend.operation.dto.responses.OperationMapResponse;
import com.sarop.saropbackend.operation.dto.responses.OperationResponse;
import com.sarop.saropbackend.operation.dto.responses.OperationTeamResponse;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operation.repository.OperationRepository;
import com.sarop.saropbackend.operation.service.OperationService;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.restapi.repository.MapRepository;
import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final TeamRepository teamRepository;

    private final MapRepository mapRepository;

    private final CategoryRepository categoryRepository;


    /*
    @PostConstruct
    public void loadDataFromAPI() {

        String apiUrl = "https://portal.akut.org.tr/api/webpage/getoperationlist";
        // Make HTTP request to fetch data from API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OperationListApiResponse> responseEntity = restTemplate.getForEntity(apiUrl, OperationListApiResponse.class);


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            OperationListApiResponse response = responseEntity.getBody();

            if (response != null && response.getOperationListApiModels() != null) {

                for (OperationApiModel operationApiModel : response.getOperationListApiModels()) {
                    Operation operation = new Operation();
                    operation.setId(operationApiModel.getOperationId());
                    operation.setOperationNumber(operationApiModel.getOperationNumber());
                    operation.setOperationDate(operationApiModel.getOperationDate());
                    operation.setName(operationApiModel.getName());
                    operationRepository.save(operation);
                    Category category = categoryRepository.findCategoryByName(operationApiModel.getCategoryName());
                    if(category == null){
                        category = new Category();
                        category.setId(Util.generateUUID());
                        if(category.getOperations() == null){
                            category.setOperations(new ArrayList<>());
                        }
                        category.getOperations().add(operation);
                        category.setName(operationApiModel.getCategoryName());
                        operation.setCategory(category);
                        categoryRepository.save(category);
                    }else{
                        category.getOperations().add(operation);
                        operation.setCategory(category);
                        categoryRepository.save(category);
                    }
                    operationRepository.save(operation);
                }
            } else {
                System.out.println("Empty or null response from the API.");
            }
        } else {
            System.out.println("Error fetching data from API. Status code: " + responseEntity.getStatusCodeValue());
        }
    }



*/



    @Override
    public Operation addOperation(OperationSaveRequest operationSaveRequest) {



        var operation = Operation.builder()
                .id(Util.generateUUID())
                .name(operationSaveRequest.getName())
                .operationDate(operationSaveRequest.getOperationDate())
                .operationNumber(operationSaveRequest.getOperationNumber())
                .maps(new ArrayList<>())
                .build();

        operationRepository.save(operation);
        if(teamRepository.findById(operationSaveRequest.getTeamId()).isPresent()){
            Team team = teamRepository.findById(operationSaveRequest.getTeamId()).orElseThrow();
            operation.setTeam(team);
            team.getOperations().add(operation);
        }
        if(categoryRepository.findById(operationSaveRequest.getCategoryId()).isPresent()){
            Category category = categoryRepository.findById(operationSaveRequest.getCategoryId()).orElseThrow();
            operation.setCategory(category);
            category.getOperations().add(operation);
        }

        if(!operationSaveRequest.getMaps().isEmpty()){
            for (String mapId : operationSaveRequest.getMaps()) {
                Map map = mapRepository.findById(mapId).orElseThrow();
                if (map != null) { // Check if map is found
                    operation.getMaps().add(map);
                    map.getOperations().add(operation); // Add operation to the map's list of operations
                }
            }

        }

        return operationRepository.save(operation);
    }


    @Override
    public Operation updateOperation(String id, OperationSaveRequest operationUpdateRequest) {


        Operation operation = operationRepository.findById(id).orElseThrow();

        operation.setName(operationUpdateRequest.getName());
        operation.setOperationNumber(operationUpdateRequest.getOperationNumber());
        operation.setOperationDate(operationUpdateRequest.getOperationDate());
        List<Map> maps = new ArrayList<>();
        if(!operationUpdateRequest.getMaps().isEmpty()){
            operation.getMaps().clear(); // Clear existing maps
            for (String mapId : operationUpdateRequest.getMaps()) {
                Map map = mapRepository.findById(mapId).orElseThrow();
                if (map != null) {
                    maps.add(map);
                    if(!map.getOperations().contains(operation)){
                        map.getOperations().add(operation);
                    }
                }
            }
            operation.getMaps().addAll(maps); // Add updated maps
        }

        if(operation.getMaps() != null){
            for(Map map : operation.getMaps()){
                if(!maps.contains(map)){
                    map.getOperations().remove(operation);
                }
            }
        }
        if(categoryRepository.findById(operationUpdateRequest.getCategoryId()).isPresent()){
            Category category = categoryRepository.findById(operationUpdateRequest.getCategoryId()).orElseThrow();
            if(operation.getCategory() != null){
                if(!operation.getCategory().equals(category)){
                    Category oldCategory = operation.getCategory();
                    operation.setCategory(category);
                    oldCategory.getOperations().remove(operation);
                }
            }else{
                operation.setCategory(category);
            }
        }

        if(teamRepository.findById(operationUpdateRequest.getTeamId()).isPresent()){
            Team team = teamRepository.findById(operationUpdateRequest.getTeamId()).orElseThrow();
            if(operation.getTeam() != null){
                if(!operation.getTeam().equals(team)){
                    Team oldTeam = operation.getTeam();
                    operation.setTeam(team);
                    oldTeam.getOperations().remove(operation);
                }
            }else{
                operation.setTeam(team);
            }

        }

        // Update the maps associated with the operation


        return operationRepository.save(operation);
    }


    @Override
    public List<OperationResponse> getAllOperations(Optional<Integer> operationNumber, Optional<String> operationDate,
                                            Optional<String> name, Optional<String> categoryName, Optional<String> teamName) {
        List<OperationResponse> operationResponses = new ArrayList<>();
        List<Operation> operations = operationRepository.findAll().stream().filter(operation ->
                (operationNumber.isEmpty() || operationNumber.get().equals(operation.getOperationNumber())) &&
                        (operationDate.isEmpty() || operationDate.get().equals(operation.getOperationDate())) &&
                        (name.isEmpty() || name.get().equals(operation.getName())) &&
                        (categoryName.isEmpty() || categoryName.get().equals(operation.getCategory().getName())) &&
                        (teamName.isEmpty() || teamName.get().equals(operation.getTeam().getName()))
        ).collect(Collectors.toList());
        for(Operation operation : operations){
            var operationResponse = OperationResponse.builder().id(operation.getId())
                    .operationDate(operation.getOperationDate()).operationNumber(operation.getOperationNumber())
                    .name(operation.getName()).maps(new ArrayList<>()).build();
            if(operation.getCategory() != null){
                CategoryResponse operationCategoryResponse = new CategoryResponse();
                operationCategoryResponse.setId(operation.getCategory().getId());
                operationCategoryResponse.setName(operation.getCategory().getName());
                operationResponse.setCategory(operationCategoryResponse);
            }
            if(operation.getTeam() != null){
                OperationTeamResponse operationTeamResponse = new OperationTeamResponse();
                operationTeamResponse.setId(operation.getTeam().getId());
                operationTeamResponse.setName(operation.getTeam().getName());
                operationResponse.setTeam(operationTeamResponse);
            }
            if(operation.getMaps() != null && !operation.getMaps().isEmpty()){
                for(Map map: operation.getMaps()){
                    OperationMapResponse operationMapResponse = new OperationMapResponse();
                    operationMapResponse.setId(map.getId());
                    operationMapResponse.setMapName(map.getMapName());
                    operationMapResponse.setWorkspaceName(map.getWorkspace().getName());
                    operationResponse.getMaps().add(operationMapResponse);
                }
            }
            operationResponses.add(operationResponse);

        }

        return operationResponses;
    }


    @Override
    public void deleteOperation(String id) {
        Operation operation = operationRepository.findById(id).orElseThrow();
        if(operation.getCategory() != null){
            Category category = operation.getCategory();
            category.getOperations().remove(operation);
            categoryRepository.save(category);
        }
        if(operation.getTeam() != null){
            Team team = operation.getTeam();
            team.getOperations().remove(operation);
            teamRepository.save(team);
        }
        if(operation.getMaps() != null){
            for(Map map: operation.getMaps()){
                map.getOperations().remove(operation);
                mapRepository.save(map);
            }
        }
        operationRepository.delete(operation);
    }
}
