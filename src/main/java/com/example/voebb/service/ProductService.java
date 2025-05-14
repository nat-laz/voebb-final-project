package com.example.voebb.service;

import com.example.voebb.model.dto.product.ProductInfoDTO;
import com.example.voebb.model.dto.product.SearchResultProductDTO;
import com.example.voebb.model.dto.product.NewProductDTO;
import com.example.voebb.model.dto.product.AdminProductDTO;
import com.example.voebb.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<SearchResultProductDTO> getAllByTitle(String title, Pageable pageable);

    Page<ProductInfoDTO> getAllByTitleAdmin(String title, Pageable pageable);

    ProductInfoDTO findById(Long id);

    AdminProductDTO createProduct(NewProductDTO dto);

    Page<Product> getAllProducts(Pageable pageable);

    void deleteProductById(Long id);

}
