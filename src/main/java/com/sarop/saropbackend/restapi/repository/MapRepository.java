package com.sarop.saropbackend.restapi.repository;

import com.sarop.saropbackend.restapi.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRepository extends JpaRepository<Map,String> {

    List<Map> findAllByWorkspaceName(String workspaceName);

    void deleteByMapName(String name);
}
