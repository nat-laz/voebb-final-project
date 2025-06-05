package com.example.voebb.model.dto.product;

import com.example.voebb.model.dto.creator.UpdateCreatorWithRoleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDTO {
    private Long id;
    private Long productTypeId;
    private String title;
    private String releaseYear;
    private String photo;
    private String description;
    private String productLinkToEmedia;
    private BookDetailsDTO bookDetails;
    private List<UpdateCreatorWithRoleDTO> creators;
    private List<Long> countryIds;
    private List<Long> languageIds;
}