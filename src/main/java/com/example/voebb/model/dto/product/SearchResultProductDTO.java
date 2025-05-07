package com.example.voebb.model.dto.product;

import com.example.voebb.model.dto.creator.CreatorRequestDTO;

import java.util.List;

/**
 * DTO with all info for ProductCard (SearchResults)
 */
public record SearchResultProductDTO(
        Long id,
        String productType,
        String title,
        String releaseYear,
        String photo,
        String description, // summary of the product based on media_type
        String productLinkToEmedia,
        CreatorRequestDTO mainCreator, // TODO: Decide which creators to show on product-card
        List<LocationAndItemsCountDTO> locations //TODO: Add locations
) {}
