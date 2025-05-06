package com.example.voebb.service;

import com.example.voebb.model.dto.product.NewBookDetailsDTO;
import com.example.voebb.model.entity.BookDetails;
import com.example.voebb.model.entity.Product;

public interface BookDetailsService {

    void saveBookDetails(NewBookDetailsDTO dto, Product product);

    BookDetails getDetailsByProductId(Long productId);

    BookDetails updateDetails(Long productId, BookDetails newDetails);

    void deleteDetails(Long productId);
}
