package com.example.voebb.model.mapper;

import com.example.voebb.model.dto.product.BookDetailsDTO;
import com.example.voebb.model.dto.product.ProductInfoDTO;
import com.example.voebb.model.entity.Country;
import com.example.voebb.model.entity.Product;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductInfoDTO toProductInfoDTO(Product product) {
        BookDetailsDTO bookDetailsDTO = BookDetailsMapper.toDto(product.getBookDetails());

        Set<String> countryNames = product.getCountries()
                .stream()
                .map(Country::getName)
                .collect(Collectors.toSet());

        return new ProductInfoDTO(
                product.getId(),
                product.getType().getName(),
                product.getTitle(),
                product.getReleaseYear(),
                product.getPhoto(),
                product.getDescription(),
                product.getProductLinkToEmedia(),
                countryNames,
                bookDetailsDTO
        );
    }
}
