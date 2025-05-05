package com.example.voebb.service.impl;


import com.example.voebb.model.entity.ProductType;
import com.example.voebb.repository.ProductTypeRepo;
import com.example.voebb.service.ProductTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepo productTypeRepo;

    public ProductTypeServiceImpl(ProductTypeRepo productTypeRepo) {
        this.productTypeRepo = productTypeRepo;
    }


    @Override
    public ProductType findByName(String name) {
        return productTypeRepo.findByNameIgnoreCase(name.trim())
                .orElseThrow(() -> new EntityNotFoundException("Product type not found: " + name));
    }
}
