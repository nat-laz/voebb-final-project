package com.example.voebb.repository;

import com.example.voebb.model.dto.item.ItemAdminDTO;
import com.example.voebb.model.entity.ProductItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductItemRepo extends JpaRepository<ProductItem, Long> {
    List<ProductItem> findAllByProductId(Long productId);

    @Query("""
        SELECT new com.example.voebb.model.dto.item.ItemAdminDTO(
            i.id,
            p.title,
            pt.name,
            s.name,
            l.name,
            loc.note
        )
        FROM ProductItem i
        JOIN i.product p
        JOIN p.type pt
        JOIN i.status s
        JOIN i.location loc
        JOIN loc.library l
    """)
    Page<ItemAdminDTO> findAllItemsForAdmin(Pageable pageable);
}
