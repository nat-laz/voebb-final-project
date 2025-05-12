package com.example.voebb.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// renamed to NewBookDetailsDTO to not conflict with record BookDetailsDTO from Alex's PR and avoid conflicts when merging - later we can decide on one version
public class NewBookDetailsDTO {
    private String isbn;
    private String edition;
    private Integer pages;
}