package com.example.voebb.service.impl;

import com.example.voebb.model.dto.creator.CreatorRequestDTO;
import com.example.voebb.model.dto.creator.CreatorResponseDTO;
import com.example.voebb.model.dto.creator.CreatorWithRoleDTO;
import com.example.voebb.model.entity.Country;
import com.example.voebb.model.entity.Creator;
import com.example.voebb.model.entity.CreatorRole;
import com.example.voebb.model.entity.Product;
import com.example.voebb.repository.CreatorRepo;
import com.example.voebb.repository.CreatorRoleRepo;
import com.example.voebb.service.CreatorProductRelationService;
import com.example.voebb.service.CreatorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatorServiceImpl implements CreatorService {

    private final CreatorRepo creatorRepo;
    private final CreatorRoleRepo creatorRoleRepo;
    private final CreatorProductRelationService creatorProductRelationService;

    public CreatorServiceImpl(CreatorRepo creatorRepo, CreatorRoleRepo creatorRoleRepo, CreatorProductRelationService creatorProductRelationService) {
        this.creatorRepo = creatorRepo;
        this.creatorRoleRepo = creatorRoleRepo;
        this.creatorProductRelationService = creatorProductRelationService;
    }




    @Override
    public void assignCreatorsToProduct(List<CreatorWithRoleDTO> creatorDTOs, Product product) {
        if (creatorDTOs == null || creatorDTOs.isEmpty()) return;

        for (CreatorWithRoleDTO creatorDTO : creatorDTOs) {

            // 1. Find or create the creator
            Creator creator = creatorRepo
                    .findByFirstNameIgnoreCaseAndLastNameIgnoreCase(
                            creatorDTO.firstName().trim(),
                            creatorDTO.lastName().trim()
                    )
                    .orElseGet(() -> {
                        Creator newCreator = new Creator();
                        newCreator.setFirstName(creatorDTO.firstName().trim());
                        newCreator.setLastName(creatorDTO.lastName().trim());
                        return creatorRepo.save(newCreator);
                    });

            // 2. Find the role
            CreatorRole role = creatorRoleRepo
                    .findByCreatorRoleIgnoreCase(creatorDTO.role().trim())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found: " + creatorDTO.role()));

            // 3. Create relation
            creatorProductRelationService.distributeInTheirTables(
                    creator.getId(),
                    product.getId(),
                    role.getId()
            );
        }
    }


    @Override
    public List<CreatorResponseDTO> getAllCreators() {
        return creatorRepo.findAll().stream()
                .map(c -> new CreatorResponseDTO(c.getId(), c.getFirstName(), c.getLastName()))
                .toList();
    }

    @Override
    public CreatorResponseDTO getCreatorById(Long id) {
        return creatorRepo.findById(id)
                .map(c -> new CreatorResponseDTO(c.getId(), c.getFirstName(), c.getLastName()))
                .orElseThrow(() -> new EntityNotFoundException("Creator not found"));
    }

    @Override
    public CreatorResponseDTO saveCreator(CreatorRequestDTO dto) {
        Creator newCreator = new Creator(null, dto.firstName(), dto.lastName());
        Creator savedCreator = creatorRepo.save(newCreator);
        return new CreatorResponseDTO(savedCreator.getId(), savedCreator.getFirstName(), savedCreator.getLastName());
    }

    @Override
    public CreatorResponseDTO updateCreator(Long id, CreatorRequestDTO dto) {
        Creator existingCreator = creatorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Creator not found"));

        existingCreator.setFirstName(dto.firstName());
        existingCreator.setLastName(dto.lastName());

        Creator updated = creatorRepo.save(existingCreator);
        return new CreatorResponseDTO(updated.getId(), updated.getFirstName(), updated.getLastName());
    }

    @Override
    public void deleteCreator(Long id) {
        if (!creatorRepo.existsById(id)) {
            throw new EntityNotFoundException("Creator not found");
        }
        creatorRepo.deleteById(id);
    }
}
