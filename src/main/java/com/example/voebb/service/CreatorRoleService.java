package com.example.voebb.service;

import com.example.voebb.model.dto.creator.CreatorRoleResponseDTO;
import com.example.voebb.model.entity.CreatorRole;

import java.util.List;

public interface CreatorRoleService {

    CreatorRole findOrCreate(String roleName);

    List<CreatorRoleResponseDTO> getAllCreatorRoles();

    CreatorRoleResponseDTO getCreatorRoleById(Long id);

    void createCreatorRole(String roleName);

    void updateCreatorRole(Long id, String roleName);

    void deleteCreatorRole(Long id);
}

