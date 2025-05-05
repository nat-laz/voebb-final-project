package com.example.voebb.service;

import com.example.voebb.model.dto.product.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDTO> getAllByTitle(String title, Pageable pageable);
}
