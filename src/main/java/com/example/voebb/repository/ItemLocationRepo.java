package com.example.voebb.repository;

import com.example.voebb.model.dto.product.LocationAndItemStatusDTO;
import com.example.voebb.model.dto.product.LocationAndItemsCountDTO;
import com.example.voebb.model.entity.ItemLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemLocationRepo extends JpaRepository<ItemLocation, Long> {
    ItemLocation getItemLocationsByItemId(Long itemId);

    @Query("""
    SELECT new com.example.voebb.model.dto.product.LocationAndItemsCountDTO(
        l.library.address.district,
        l.library.name,
        COUNT(i)
    )
    FROM ProductItem i
    JOIN i.location l
    WHERE i.product.id = :productId
      AND i.status.id = 1
    GROUP BY l.library.address.district, l.library.name
    """)
    List<LocationAndItemsCountDTO> getLocationAndItemsCount(@Param("productId") Long productId);

    @Query("""
    SELECT new com.example.voebb.model.dto.product.LocationAndItemStatusDTO(
        l.library.name,
        l.library.address.district,
        l.item.status.name,
        l.note
    )
    FROM ProductItem i
    JOIN i.location l
    WHERE i.product.id = :productId
    GROUP BY l.library.address.district, l.library.name, l.item.status.name, l.note
    """)
    List<LocationAndItemStatusDTO> getItemLocationsFullInfo(@Param("productId") Long productId);

}
