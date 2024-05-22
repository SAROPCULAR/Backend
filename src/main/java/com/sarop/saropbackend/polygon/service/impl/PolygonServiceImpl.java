package com.sarop.saropbackend.polygon.service.impl;

import com.sarop.saropbackend.common.Coordinate;
import com.sarop.saropbackend.common.CoordinateRepository;
import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.polygon.dto.PolygonAddRequest;
import com.sarop.saropbackend.polygon.dto.PolygonMapResponse;
import com.sarop.saropbackend.polygon.dto.PolygonResponse;
import com.sarop.saropbackend.polygon.model.Polygon;
import com.sarop.saropbackend.polygon.repository.PolygonRepository;
import com.sarop.saropbackend.polygon.service.PolygonService;
import com.sarop.saropbackend.restapi.dto.Responses.CoordinateResponse;
import com.sarop.saropbackend.restapi.entity.Map;
import com.sarop.saropbackend.restapi.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PolygonServiceImpl implements PolygonService {

    private final PolygonRepository polygonRepository;
    private final MapRepository mapRepository;

    private final CoordinateRepository coordinateRepository;
    @Override
    @Transactional
    public Polygon addPolygon(PolygonAddRequest polygonAddRequest) {
        Map map = mapRepository.findById(polygonAddRequest.getMapId()).orElseThrow();
        Polygon polygon = Polygon.builder().id(Util.generateUUID()).map(map).coordinates(new ArrayList<>()).build();
        for(List<Double> coordinate: polygonAddRequest.getCoordinates() ){
            Coordinate polygonCoordinate = new Coordinate();
            polygonCoordinate.setX(coordinate.get(0));
            polygonCoordinate.setY(coordinate.get(1));
            coordinateRepository.save(polygonCoordinate);
            polygon.getCoordinates().add(polygonCoordinate);
        }
        polygonRepository.save(polygon);
        map.getPolygons().add(polygon);
      //  mapRepository.save(map);

        return polygon;
    }

    @Override
        public List<PolygonResponse> getAllPolygonsByMapId(String id) {
        List<PolygonResponse> polygonResponses = new ArrayList<>();
        List<Polygon> polygonList =  polygonRepository.getPolygonsByMap_Id(id);
        if(polygonList != null){
            for(Polygon polygon : polygonList) {
                PolygonResponse polygonResponse = new PolygonResponse();
                polygonResponse.setId(polygon.getId());
                polygonResponse.setCoordinates(new ArrayList<>());
                for (Coordinate coordinate : polygon.getCoordinates()) {
                    CoordinateResponse coordinateResponse = new CoordinateResponse();
                    coordinateResponse.setId(coordinate.getId());
                    coordinateResponse.setX(coordinate.getX());
                    coordinateResponse.setY(coordinate.getY());
                    polygonResponse.getCoordinates().add(coordinateResponse);
                }
                PolygonMapResponse polygonMapResponse = new PolygonMapResponse();
                polygonMapResponse.setId(polygon.getMap().getId());
                polygonMapResponse.setMapName(polygon.getMap().getMapName());
                polygonMapResponse.setWorkspaceName(polygon.getMap().getWorkspace().getName());
                polygonResponse.setMap(polygonMapResponse);
                polygonResponses.add(polygonResponse);
            }
        }
        return polygonResponses;
    }

    @Override
    public void deletePolygon(String id) {
        Polygon polygon = polygonRepository.findById(id).orElseThrow();
        Map map = polygon.getMap();
        map.getPolygons().remove(polygon);
        mapRepository.save(map);
        polygonRepository.delete(polygon);
    }
}
