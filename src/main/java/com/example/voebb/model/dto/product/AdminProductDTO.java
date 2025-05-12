package com.example.voebb.model.dto.product;

// used as a return output in productServiceImpl.createProduct
// not sure if we need more details here
public record AdminProductDTO(
        Long id,
        String title,
        String productType
) {}