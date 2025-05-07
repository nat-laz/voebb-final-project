package com.example.voebb.model.dto.product;

import com.example.voebb.model.dto.creator.CreatorWithRoleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewProductDTO {

    String productType;          // sanitized String: "book", "ebook", "movie"
    String title;
    String releaseYear;
    String photo;
    String description;
    String productLinkToEmedia;

    NewBookDetailsDTO bookDetails;   // nullable for non-books

    List<CreatorWithRoleDTO> creators = new ArrayList<>();
}
