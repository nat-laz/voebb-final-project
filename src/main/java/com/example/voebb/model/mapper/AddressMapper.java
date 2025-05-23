package com.example.voebb.model.mapper;

import com.example.voebb.model.dto.library.CreateLibraryDTO;
import com.example.voebb.model.dto.library.EditLibraryDTO;
import com.example.voebb.model.entity.Address;

public class AddressMapper {

    public static Address toEntity(EditLibraryDTO dto) {
        return new Address(
                dto.city(),
                dto.district(),
                dto.postcode(),
                dto.street(),
                dto.houseNumber(),
                dto.osmLink()
        );
    }

    public static Address toEntity(CreateLibraryDTO dto) {
        return new Address(
                dto.getCity(),
                dto.getDistrict(),
                dto.getPostcode(),
                dto.getStreet(),
                dto.getHouseNumber(),
                dto.getOsmLink()
        );
    }
}
