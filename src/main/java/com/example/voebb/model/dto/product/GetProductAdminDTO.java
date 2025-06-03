package com.example.voebb.model.dto.product;

public record GetProductAdminDTO(
        Long id,
        String title,
        String productType,
        String mainCreator,
        String productLinkToEmedia
) {
}
