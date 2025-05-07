package com.example.voebb.service;

import com.example.voebb.model.dto.product.LocationAndItemStatusDTO;
import com.example.voebb.model.dto.product.LocationAndItemsCountDTO;
import com.example.voebb.model.entity.ProductItem;

import java.util.List;

public interface ProductItemService {
    List<ProductItem> getAllByProductId(Long productId);

    List<LocationAndItemsCountDTO> getLocationsForAvailableItemsByProductId(Long productId);

    List<LocationAndItemStatusDTO> getAllLocationsForProduct(Long productId);
}
