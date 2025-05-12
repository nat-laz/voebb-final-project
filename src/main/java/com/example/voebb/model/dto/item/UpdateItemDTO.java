package com.example.voebb.model.dto.item;

public record UpdateItemDTO(
        Long itemId,
        Long statusId,
        Long libraryId,
        String locationNote
) {
}
