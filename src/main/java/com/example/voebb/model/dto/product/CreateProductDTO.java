package com.example.voebb.model.dto.product;

import com.example.voebb.model.dto.creator.CreatorWithRoleDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {

    String productType;          // TODO:  Long typeId;
    String title;
    String releaseYear;
    String photo;
    String description;
    String productLinkToEmedia;
    BookDetailsDTO bookDetails;
    List<CreatorWithRoleDTO> creators;
    List<Long> countryIds;


    // TODO: List<Long> languageIds;

}
