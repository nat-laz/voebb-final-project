package com.example.voebb.service;


import com.example.voebb.model.dto.creator.CreatorWithRoleDTO;

import java.util.List;

public interface CreatorProductRelationService {

    void distributeInTheirTables(Long creatorId, Long productId, Long roleId);

}

