package com.example.voebb.service;

import com.example.voebb.model.dto.product.BookDetailsDTO;
import com.example.voebb.model.entity.Product;

public interface BookDetailsService {

    void saveBookDetails(BookDetailsDTO dto, Product product);

    BookDetailsDTO getDetailsDTOByProductId(Long productId);

    void updateDetails(Product product, BookDetailsDTO newDetails);

    void deleteDetails(Long productId);
}
