package com.example.voebb.service.impl;

import com.example.voebb.model.dto.CreatorDTO;
import com.example.voebb.model.entity.Creator;
import com.example.voebb.repository.CreatorRepo;
import com.example.voebb.service.CreatorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatorServiceImpl implements CreatorService {

    private final CreatorRepo creatorRepo;

    public CreatorServiceImpl(CreatorRepo creatorRepo) {
        this.creatorRepo = creatorRepo;
    }


    @Override
    public List<CreatorDTO> findAll() {
        return creatorRepo.findAll().stream()
                .map(c -> new CreatorDTO(c.getFirstName(), c.getLastName()))
                .toList();
    }

    @Override
    public CreatorDTO findById(Long id) {
        return creatorRepo.findById(id)
                .map(c -> new CreatorDTO(c.getFirstName(), c.getLastName()))
                .orElseThrow(() -> new EntityNotFoundException("Creator not found"));
    }

    @Override
    public CreatorDTO create(CreatorDTO dto) {
        Creator newCreator = new Creator(null, dto.firstName(), dto.lastName());
        Creator savedCreator = creatorRepo.save(newCreator);
        return new CreatorDTO(savedCreator.getFirstName(), savedCreator.getLastName());
    }

    @Override
    public CreatorDTO update(Long id, CreatorDTO dto) {

        Creator existingCreator = creatorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Creator not found"));
        existingCreator.setFirstName(dto.firstName());
        existingCreator.setLastName(dto.lastName());

        Creator updated = creatorRepo.save(existingCreator);
        return new CreatorDTO(updated.getFirstName(), updated.getLastName());
    }

    @Override
    public void delete(Long id) {
        creatorRepo.deleteById(id);
    }
}
