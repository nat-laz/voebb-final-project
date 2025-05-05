package com.example.voebb.service;


import com.example.voebb.model.entity.ProductType;

public interface ProductTypeService {

    ProductType findByName(String name);

}
