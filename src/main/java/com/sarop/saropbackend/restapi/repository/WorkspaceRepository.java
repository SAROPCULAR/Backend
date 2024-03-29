package com.sarop.saropbackend.restapi.repository;

import com.sarop.saropbackend.restapi.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace,String> {

    void deleteByName(String workspaceName);

    Optional<Workspace> findWorkspaceByName(String name);
}
