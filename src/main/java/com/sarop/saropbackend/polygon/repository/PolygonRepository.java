package com.sarop.saropbackend.polygon.repository;

import com.sarop.saropbackend.polygon.model.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolygonRepository extends JpaRepository<Polygon,String> {
    List<Polygon> getPolygonsByMap_Id(String id);
}
