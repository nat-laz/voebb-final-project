package com.example.voebb.repository;

import com.example.voebb.model.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepo extends JpaRepository<ProductType, Long> {

    Optional<ProductType> findByNameIgnoreCase(String name);
}
