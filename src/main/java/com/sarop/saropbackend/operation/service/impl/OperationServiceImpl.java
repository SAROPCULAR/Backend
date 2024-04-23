package com.sarop.saropbackend.operation.service.impl;

import com.sarop.saropbackend.category.model.Category;
import com.sarop.saropbackend.category.repository.CategoryRepository;
import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
import com.sarop.saropbackend.operation.dto.apimodels.OperationApiModel;
import com.sarop.saropbackend.operation.dto.apiresponse.OperationListApiResponse;
import com.sarop.saropbackend.operation.model.Operation;
import com.sarop.saropbackend.operation.repository.OperationRepository;
import com.sarop.saropbackend.operation.service.OperationService;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.restapi.repository.MapRepository;
import com.sarop.saropbackend.team.model.Team;
import com.sarop.saropbackend.team.repository.TeamRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    @Override
    public Operation addOperation(OperationSaveRequest operationSaveRequest) {
        Team team = teamRepository.findTeamByName(operationSaveRequest.getTeamName());
        Category category = categoryRepository.findCategoryByName(operationSaveRequest.getCategoryName());
        var operation = Operation.builder().id(Util.generateUUID())
                .name(operationSaveRequest.getName())
                .operationDate(operationSaveRequest.getOperationDate())
                .operationNumber(operationSaveRequest.getOperationNumber())
                .team(team).category(category).maps(new ArrayList<Map>()).build();
        for(String mapName : operationSaveRequest.getMaps()){
            Map map = mapRepository.findMapByMapName(mapName);
            operation.getMaps().add(map);
        }
        return operationRepository.save(operation);
    }

    @Override
    public Operation updateOperation(String id, OperationSaveRequest operationUpdateRequest) {
        Team team = teamRepository.findTeamByName(operationUpdateRequest.getTeamName());
        Category category = categoryRepository.findCategoryByName(operationUpdateRequest.getCategoryName());
        List<Map> maps = new ArrayList<Map>();
        for(String mapName : operationUpdateRequest.getMaps()){
            Map map = mapRepository.findMapByMapName(mapName);
            maps.add(map);
        }
        Operation operation = operationRepository.findById(id).orElseThrow();
        operation.setName(operationUpdateRequest.getName());
        operation.setOperationNumber(operationUpdateRequest.getOperationNumber());
        operation.setOperationDate(operationUpdateRequest.getOperationDate());
        operation.setCategory(category);
        operation.setTeam(team);
        operation.setMaps(maps);
        return operationRepository.save(operation);

    }

    @Override
    public List<Operation> getAllOperations(Optional<Integer> operationNumber,Optional<String> operationDate,
                                            Optional<String> name,Optional<String> categoryName,Optional<String> teamName
                                            ) {
        List<Operation> operations = operationRepository.findAll().stream().filter(operation ->
                        (!operationNumber.isPresent() || operationNumber.equals(operation.getOperationNumber())) ||
                                (!operationDate.isPresent() || operation.getOperationDate().equals(operationDate)) ||
                                (!name.isPresent() || operation.getName().equals(name)) ||
                                (!categoryName.isPresent() || operation.getCategory().getName().equals(categoryName))
                || (!teamName.isPresent() || operation.getTeam().getName().equals(teamName))
                ).collect(Collectors.toList());
        return operations;
    }

    @Override
    public void deleteOperation(String id) {
        operationRepository.deleteById(id);
    }
}
