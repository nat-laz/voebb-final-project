package com.example.voebb.service;

import com.example.voebb.model.dto.CreatorDTO;

import java.util.List;

public interface CreatorService {
    List<CreatorDTO> findAll();
    CreatorDTO findById(Long id);
    CreatorDTO create(CreatorDTO dto);
    CreatorDTO update(Long id, CreatorDTO dto);
    void delete(Long id);
}
