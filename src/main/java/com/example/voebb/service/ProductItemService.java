package com.example.voebb.service;

import com.example.voebb.model.dto.item.CreateItemDTO;
import com.example.voebb.model.dto.item.ItemAdminDTO;
import com.example.voebb.model.dto.item.UpdateItemDTO;
import com.example.voebb.model.dto.product.LocationAndItemStatusDTO;
import com.example.voebb.model.dto.product.LocationAndItemsCountDTO;
import com.example.voebb.model.entity.ProductItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductItemService {
    List<ProductItem> getAllByProductId(Long productId);

    List<LocationAndItemsCountDTO> getLocationsForAvailableItemsByProductId(Long productId);

    List<LocationAndItemStatusDTO> getAllLocationsForProduct(Long productId);

    Page<ItemAdminDTO> getAllItems(Pageable pageable);

    void createItem(CreateItemDTO dto);

    void editItem(UpdateItemDTO dto);

    void deleteItemById(Long id);

}
