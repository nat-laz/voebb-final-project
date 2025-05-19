package com.example.voebb.service;

import com.example.voebb.model.dto.product.*;
import com.example.voebb.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<CardProductDTO> getProductCardsByTitle(String title, Pageable pageable);

    Page<ProductInfoDTO> getAllByTitleAdmin(String title, Pageable pageable);

    ProductInfoDTO findById(Long id);

    void createProduct(CreateProductDTO dto);

    Page<Product> getAllProducts(Pageable pageable);

    void deleteProductById(Long id);

    UpdateProductDTO getProductById(Long id);

    UpdateProductDTO updateProduct(Long productId, UpdateProductDTO updateProductDTO);

    void saveProduct(Product existingProduct);
}
