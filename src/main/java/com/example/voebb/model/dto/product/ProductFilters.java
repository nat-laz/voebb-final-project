package com.example.voebb.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ProductFilters {
    // Product filters
    private String title;
    private String author;

    // Book filters
    private Long libraryId;
}
