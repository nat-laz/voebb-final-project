package com.example.voebb.model.dto.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLibraryDTO {
    private String name;
    private String description;
    private String postcode;
    private String city;
    private String district;
    private String street;
    private String houseNumber;
    private String osmLink;
}
