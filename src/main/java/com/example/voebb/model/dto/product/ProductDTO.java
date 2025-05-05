package com.example.voebb.model.dto.product;

import com.example.voebb.model.dto.creator.CreatorRequestDTO;

public record ProductDTO(
     Long id,
     String productType,
     String title,
     String releaseYear,
     String photo,
     String description, // summary of the product based on media_type
     String productLinkToEmedia,
     CreatorRequestDTO mainCreator // TODO: convert to list of full-names of all main creators
) {}
