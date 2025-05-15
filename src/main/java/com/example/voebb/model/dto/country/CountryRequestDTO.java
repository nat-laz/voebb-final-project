package com.example.voebb.model.dto.country;

import lombok.Data;
import java.util.Set;

@Data
public class CountryRequestDTO {
    private Set<Long> countryIds;
}