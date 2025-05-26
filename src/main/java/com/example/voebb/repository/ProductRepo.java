package com.example.voebb.repository;

import com.example.voebb.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = """
            SELECT  distinct p
            FROM Product p
            LEFT JOIN ProductItem pi ON pi.product = p
            LEFT JOIN ItemLocation il ON il.item = pi
            LEFT JOIN Library lib ON il.library = lib
            LEFT JOIN  CreatorProductRelation cpr ON cpr.product = p
            LEFT JOIN Creator c on cpr.creator = c
            WHERE(:title = '' or :title is null OR p.title ILIKE '%' || :title || '%')
            AND (:libraryId IS NULL OR lib.id = :libraryId)
            AND (:author = '' or :author is null or c.lastName ILIKE '%' || :author || '%' OR c.firstName ILIKE '%' || :author || '%')
            """)
    Page<Product> searchWithFilters(@Param("title") String title,
                                    @Param("libraryId") Long libraryId,
                                    @Param("author") String author,
                                    Pageable pageable);

    Optional<Product> getProductById(Long id);

}
