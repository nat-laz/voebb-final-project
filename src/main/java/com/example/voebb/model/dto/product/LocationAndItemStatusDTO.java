package com.example.voebb.model.dto.product;

public record LocationAndItemStatusDTO(
        String libraryName,
        String district,
        String itemStatus,
        String locationInLibrary
        // TODO: Available date (if item is borrowed/reserved)
) {
}
