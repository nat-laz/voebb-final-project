package com.example.voebb.model.dto.item;

public record CreateItemDTO(
        Long productId,
        Long libraryId,
        String locationNote
) {}