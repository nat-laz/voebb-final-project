package com.example.voebb.model.dto.product;

/**
 * DTO with all info from Product
 */
public record ProductInfoDTO(
        Long id,
        String productType,
        String title,
        String releaseYear,
        String photo,
        String description, // summary of the product based on media_type
        String productLinkToEmedia,
        BookDetailsDTO bookDetails //  null for non-books
        //TODO: add Languages, Countries, Creators
) {
}

