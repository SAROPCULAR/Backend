package com.sarop.saropbackend.operation.service.impl;

import com.sarop.saropbackend.category.model.Category;
import com.sarop.saropbackend.category.repository.CategoryRepository;
import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.operation.dto.OperationSaveRequest;
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

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final TeamRepository teamRepository;

    private final MapRepository mapRepository;

    private final CategoryRepository categoryRepository;
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
    public List<Operation> getAllOperations() {
        return operationRepository.findAll();
    }

    @Override
    public void deleteOperation(String id) {
        operationRepository.deleteById(id);
    }
}
