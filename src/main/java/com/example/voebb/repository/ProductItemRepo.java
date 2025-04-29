package com.example.voebb.repository;

import com.example.voebb.model.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepo extends JpaRepository<ProductItem, Long> {
}
