package com.example.voebb.service;

import com.example.voebb.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getAllByTitle(String title, Pageable pageable);
}
