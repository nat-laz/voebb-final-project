package com.example.voebb.model.dto.product;

public record LocationAndItemsCountDTO(
        String district,
        String libraryName,
        Long itemsCount
) {
}
