package com.example.voebb.service;

import com.example.voebb.model.dto.product.ProductRequestDTO;
import com.example.voebb.model.dto.product.ProductResponseDTO;
import com.example.voebb.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getAllByTitle(String title, Pageable pageable);

    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
}
