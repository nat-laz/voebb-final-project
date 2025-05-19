package com.example.voebb.service.impl;

import com.example.voebb.model.dto.creator.CreatorFullNameDTO;
import com.example.voebb.model.dto.creator.CreatorWithRoleDTO;
import com.example.voebb.model.entity.*;
import com.example.voebb.repository.CreatorProductRelationRepo;
import com.example.voebb.repository.CreatorRepo;
import com.example.voebb.repository.CreatorRoleRepo;
import com.example.voebb.repository.ProductRepo;
import com.example.voebb.service.CreatorProductRelationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreatorProductRelationServiceImpl implements CreatorProductRelationService {

    private final CreatorProductRelationRepo relationRepo;
    private final CreatorRepo creatorRepo;
    private final ProductRepo productRepo;
    private final CreatorRoleRepo creatorRoleRepo;

    @Override
    public void distributeInTheirTables(Long creatorId, Long productId, Long roleId) {


        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Creator creator = creatorRepo.findById(creatorId)
                .orElseThrow(() -> new EntityNotFoundException("Creator not found"));
        CreatorRole role = creatorRoleRepo.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        CreatorProductRelation relation = new CreatorProductRelation();
        relation.setProduct(product);
        relation.setCreator(creator);
        relation.setCreatorRole(role);
        relation.setId(new CreatorRelationId(creatorId, productId, roleId));

        relationRepo.save(relation);
    }

    @Override
    public List<CreatorWithRoleDTO> getCreatorsByProductId(Long productId) {
        return relationRepo.findByProductId(productId).stream()
                .map(relation -> new CreatorWithRoleDTO(
                        relation.getCreator().getFirstName(),
                        relation.getCreator().getLastName(),
                        relation.getCreatorRole().getCreatorRoleName()
                ))
                .toList();
    }

    @Override
    public List<CreatorFullNameDTO> getMainCreators(Long productId, Long creatorRoleId) {
        return relationRepo.findByProductId(productId).stream()
                .filter(relation -> Objects.equals(relation.getCreatorRole().getId(), creatorRoleId))
                .map(relation -> new CreatorFullNameDTO(
                        relation.getCreator().getFirstName(),
                        relation.getCreator().getLastName()
                ))
                .toList();
    }
}
