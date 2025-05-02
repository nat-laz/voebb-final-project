package com.example.voebb.service.impl;

import com.example.voebb.model.entity.Product;
import com.example.voebb.repository.ProductRepo;
import com.example.voebb.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    @Override
    public Page<Product> getAllByTitle(String title, Pageable pageable) {
        return productRepo.findAllByTitleContainsIgnoreCase(title, pageable);
    }

}
