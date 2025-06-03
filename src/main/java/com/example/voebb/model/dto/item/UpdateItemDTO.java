package com.example.voebb.model.dto.item;

public record UpdateItemDTO(
        Long statusId,
        Long libraryId,
        String locationNote
) {
}
