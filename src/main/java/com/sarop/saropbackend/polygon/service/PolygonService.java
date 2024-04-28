package com.sarop.saropbackend.polygon.service;

import com.sarop.saropbackend.polygon.dto.PolygonAddRequest;
import com.sarop.saropbackend.polygon.model.Polygon;

import java.util.List;

public interface PolygonService {

    Polygon addPolygon(PolygonAddRequest polygonAddRequest);

    List<Polygon> getAllPolygonsByMapId(String id);

    void deletePolygon(String id);
}
