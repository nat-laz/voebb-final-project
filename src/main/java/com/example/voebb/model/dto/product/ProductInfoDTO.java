package com.example.voebb.model.dto.product;

import java.util.Set;

/**
    DTO with all info from Product
 */
public record ProductInfoDTO(
        Long id,
        String productType,
        String title,
        String releaseYear,
        String photo,
        String description, // summary of the product based on media_type
        String productLinkToEmedia,
        Set<String> countries,
        BookDetailsDTO bookDetails //  null for non-books
        //TODO: add Languages, Creators
) {
}
