package com.example.voebb.model.dto.product;

import com.example.voebb.model.dto.creator.CreatorWithRoleDTO;

import java.util.List;

public record ProductRequestDTO(
        String productType,                   // sanitized String: "book", "ebook"
        String title,
        String releaseYear,
        String photo,
        String description,
        String productLinkToEmedia,
        BookDetailsDTO bookDetails,               // nullable for non-books
        List<CreatorWithRoleDTO> creators,        // List of creators with their role
        String language,                          // drop-down existing one in FE
        String country                            // drop-down existing one in FE
) {
}

