package com.example.voebb.repository;

import com.example.voebb.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findAllByTitleContainsIgnoreCase(String title, Pageable pageable);

    Page<Product> findAllByTitle(String title, Pageable pageable);
}
