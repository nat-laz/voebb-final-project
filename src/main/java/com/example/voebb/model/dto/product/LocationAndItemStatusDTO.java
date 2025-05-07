package com.example.voebb.model.dto.product;

public record LocationAndItemStatusDTO(
        String libraryName,
        String district,
        String itemStatus
        // TODO: Available date (if item is borrowed/reserved)
) {
}
