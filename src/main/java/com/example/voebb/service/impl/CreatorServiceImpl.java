package com.example.voebb.service.impl;

import com.example.voebb.model.dto.creator.CreatorFullNameDTO;
import com.example.voebb.model.dto.creator.CreatorResponseDTO;
import com.example.voebb.model.dto.creator.CreatorWithRoleDTO;
import com.example.voebb.model.dto.creator.UpdateCreatorWithRoleDTO;
import com.example.voebb.model.entity.Creator;
import com.example.voebb.model.entity.CreatorProductRelation;
import com.example.voebb.model.entity.CreatorRole;
import com.example.voebb.model.entity.Product;
import com.example.voebb.repository.CreatorProductRelationRepo;
import com.example.voebb.repository.CreatorRepo;
import com.example.voebb.repository.CreatorRoleRepo;
import com.example.voebb.service.CreatorRoleService;
import com.example.voebb.service.CreatorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {

    private final CreatorRepo creatorRepo;
    private final CreatorRoleService creatorRoleService;
    private final CreatorProductRelationRepo creatorProductRelationRepo;
    private final CreatorRoleRepo creatorRoleRepo;

    @Override
    @Transactional
    public void assignCreatorsToProduct(List<CreatorWithRoleDTO> creatorDTOs,
                                        Product product) {
        if (creatorDTOs == null || creatorDTOs.isEmpty()) return;

        for (CreatorWithRoleDTO dto : creatorDTOs) {

            String firstName = dto.getFirstName() == null ? "" : dto.getFirstName().trim();
            String lastName = dto.getLastName() == null ? "" : dto.getLastName().trim();
            String roleName = dto.getRole() == null ? "" : dto.getRole().trim();

            Creator creator = creatorRepo
                    .findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName)
                    .orElseGet(() -> {
                        Creator c = new Creator();
                        c.setFirstName(firstName);
                        c.setLastName(lastName);
                        return creatorRepo.save(c);
                    });

            CreatorRole role = creatorRoleService.findOrCreate(roleName);

            CreatorProductRelation relation = new CreatorProductRelation();
            product.addRelation(relation);
            creator.addRelation(relation);
            relation.setCreatorRole(role);
        }
    }


    @Override
    @Transactional
    public void updateCreatorWithRolesForProduct(Product product, List<UpdateCreatorWithRoleDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            throw new IllegalArgumentException("At least one creator must be assigned.");
        }

        List<CreatorProductRelation> existingRelations = new ArrayList<>(product.getCreatorProductRelations());
        List<CreatorProductRelation> updatedRelations = new ArrayList<>();

        for (UpdateCreatorWithRoleDTO dto : dtos) {
            Long creatorId = dto.getCreatorId();
            Long roleId = dto.getCreatorRoleId();

            Creator creator = creatorRepo.findById(creatorId)
                    .orElseThrow(() -> new RuntimeException("Creator not found: " + creatorId));

            CreatorRole role = creatorRoleRepo.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("CreatorRole not found: " + roleId));

            CreatorProductRelation match = existingRelations.stream()
                    .filter(relation ->
                            relation.getCreator().getId().equals(creatorId)
                            && relation.getCreatorRole().getId().equals(roleId))
                    .findFirst()
                    .orElse(null);

            if (match != null) {
                updatedRelations.add(match);
            } else {
                CreatorProductRelation newRel = new CreatorProductRelation();
                product.addRelation(newRel);
                creator.addRelation(newRel);
                newRel.setCreatorRole(role);
                updatedRelations.add(newRel);
            }
        }

        // Remove obsolete relations
        List<CreatorProductRelation> toRemove = existingRelations.stream()
                .filter(relation -> updatedRelations.stream().noneMatch(updated ->
                        updated.getCreator().getId().equals(relation.getCreator().getId())
                        && updated.getCreatorRole().getId().equals(relation.getCreatorRole().getId())))
                .toList();

        for (CreatorProductRelation relation : toRemove) {
            relation.getCreator().getCreatorProductRelations().remove(relation);
            relation.setCreator(null);
            relation.setProduct(null);
            relation.setCreatorRole(null);
        }

        product.getCreatorProductRelations().removeAll(toRemove);
        creatorProductRelationRepo.deleteAll(toRemove);
    }


    @Override
    public Set<CreatorResponseDTO> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }

        String[] nameParts = name.split(" ");

        Set<CreatorResponseDTO> creators = new HashSet<>();

        for (var namePart : nameParts) {
            creatorRepo.searchByNameNative(namePart)
                    .stream()
                    .map(creator -> new CreatorResponseDTO(
                            creator.getId(),
                            creator.getFirstName(),
                            creator.getLastName()
                    ))
                    .forEach(creators::add);
        }
        return creators;
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
    @Transactional
    public CreatorResponseDTO saveCreator(CreatorFullNameDTO dto) {
        Creator newCreator = new Creator();
        newCreator.setFirstName(dto.firstName());
        newCreator.setLastName(dto.lastName());
        Creator savedCreator = creatorRepo.save(newCreator);
        return new CreatorResponseDTO(savedCreator.getId(), savedCreator.getFirstName(), savedCreator.getLastName());
    }

    @Override
    public CreatorResponseDTO updateCreator(Long id, CreatorFullNameDTO dto) {
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

    @Override
    public List<CreatorWithRoleDTO> getCreatorsWithRolesByProductId(Long id) {
        return creatorProductRelationRepo.findByProductId(id).stream()
                .map(rel -> new CreatorWithRoleDTO(
                        rel.getCreator().getFirstName(),
                        rel.getCreator().getLastName(),
                        rel.getCreatorRole().getCreatorRoleName()
                ))
                .collect(Collectors.toList());
    }
}
