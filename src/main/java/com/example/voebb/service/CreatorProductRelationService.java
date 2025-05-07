package com.example.voebb.service;


import com.example.voebb.model.dto.creator.CreatorWithRoleDTO;
import com.example.voebb.model.dto.creator.CreatorWithRoleDTO2;

import java.util.List;

public interface CreatorProductRelationService {

    void distributeInTheirTables(Long creatorId, Long productId, Long roleId);

    List<CreatorWithRoleDTO2> getCreatorsByProductId(Long productId);
}

