package com.example.voebb.model.dto.item;

public record ItemAdminDTO(
        Long itemId,
        String productTitle,
        String productType,
        String itemStatus,
        String libraryName,
        String locationNote
) {}

