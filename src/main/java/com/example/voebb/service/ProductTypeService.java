package com.example.voebb.service;


import com.example.voebb.model.entity.ProductType;

public interface ProductTypeService {

    ProductType findOrCreate(String productTypeName);

    ProductType findByName(String name);

}
