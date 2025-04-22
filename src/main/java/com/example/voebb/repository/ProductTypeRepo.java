package com.example.voebb.repository;

import com.example.voebb.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepo extends JpaRepository<ProductType, Long> {
}
