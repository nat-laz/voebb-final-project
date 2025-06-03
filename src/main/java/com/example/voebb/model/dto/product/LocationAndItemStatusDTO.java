package com.example.voebb.model.dto.product;

public record LocationAndItemStatusDTO(
        Long libraryId,
        Long itemId,
        String libraryName,
        String district,
        String itemStatus,
        String locationInLibrary
        // TODO: Available date (if item is borrowed/reserved)
) {
}
