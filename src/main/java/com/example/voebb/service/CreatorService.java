package com.example.voebb.service;

import com.example.voebb.model.dto.creator.CreatorRequestDTO;
import com.example.voebb.model.dto.creator.CreatorResponseDTO;
import com.example.voebb.model.dto.creator.CreatorWithRoleDTO;
import com.example.voebb.model.entity.Country;
import com.example.voebb.model.entity.Product;

import java.util.List;

public interface CreatorService {

    void assignCreatorsToProduct(List<CreatorWithRoleDTO> creatorDTOs, Product product);

    List<CreatorResponseDTO> getAllCreators();

    CreatorResponseDTO getCreatorById(Long id);

    CreatorResponseDTO saveCreator(CreatorRequestDTO dto);

    CreatorResponseDTO updateCreator(Long id, CreatorRequestDTO dto);

    void deleteCreator(Long id);
}
