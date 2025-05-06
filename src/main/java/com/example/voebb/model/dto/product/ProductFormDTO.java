package com.example.voebb.model.dto.product;

import com.example.voebb.model.dto.creator.CreatorWithRoleDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductFormDTO {

    @NotBlank
    private String productType;

    @NotBlank
    private String title;

    private String releaseYear;
    private String photo;
    private String description;
    private String productLinkToEmedia;

    // book details
    private String isbn;
    private String edition;
    private Integer pages;

    private List<CreatorWithRoleDTO> creators = new ArrayList<>();

    @NotBlank
    private String language;

    @NotBlank
    private String country;

    public ProductRequestDTO mapToRequestDTO() {
        BookDetailsDTO bd = (isbn == null && edition == null && pages == null)
                ? null
                : new BookDetailsDTO(isbn, edition, pages);
        return new ProductRequestDTO(
                productType, title, releaseYear, photo, description,
                productLinkToEmedia, bd, creators, language, country);
    }

}
