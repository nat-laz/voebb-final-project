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
import com.example.voebb.service.CreatorRoleService;
import com.example.voebb.service.CreatorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatorServiceImpl implements CreatorService {

    private final CreatorRepo creatorRepo;
    private final CreatorProductRelationService creatorProductRelationService;
    private final CreatorRoleService creatorRoleService;

    @Autowired
    public CreatorServiceImpl(
            CreatorRepo creatorRepo,
            CreatorProductRelationService creatorProductRelationService, CreatorRoleService creatorRoleService) {
        this.creatorRepo = creatorRepo;
        this.creatorProductRelationService = creatorProductRelationService;
        this.creatorRoleService = creatorRoleService;

    }


    @Override
    @Transactional
    public void assignCreatorsToProduct(List<CreatorWithRoleDTO> creatorDTOs,
                                        Product product) {

        if (creatorDTOs == null || creatorDTOs.isEmpty()) return;

        for (CreatorWithRoleDTO dto : creatorDTOs) {

            // TODO: proper validation & sanitization
            if ((dto.getFirstName() == null || dto.getFirstName().isBlank())
                    && (dto.getLastName() == null || dto.getLastName().isBlank())) {
                continue;
            }

            String first = dto.getFirstName() == null ? "" : dto.getFirstName().trim();
            String last  = dto.getLastName()  == null ? "" : dto.getLastName().trim();
            String roleName = dto.getRole()   == null ? "" : dto.getRole().trim();

            // 1. Find or create the creator
            Creator creator = creatorRepo
                    .findByFirstNameIgnoreCaseAndLastNameIgnoreCase(first, last)
                    .orElseGet(() -> {
                        Creator c = new Creator();
                        c.setFirstName(first);
                        c.setLastName(last);
                        return creatorRepo.save(c);
                    });

            // 2. Find or create the role
            CreatorRole role = creatorRoleService.findOrCreate(roleName);

            // 3. Create relation
            creatorProductRelationService.distributeInTheirTables(
                    creator.getId(), product.getId(), role.getId());
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
