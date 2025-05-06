package com.example.voebb.service;

import com.example.voebb.model.dto.product.ProductInfoDTO;
import com.example.voebb.model.dto.product.SearchResultProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<SearchResultProductDTO> getAllByTitle(String title, Pageable pageable);

    ProductInfoDTO findById(Long id);
}
