package com.example.voebb.service.impl;

import com.example.voebb.model.dto.item.ItemAdminDTO;
import com.example.voebb.model.dto.product.LocationAndItemStatusDTO;
import com.example.voebb.model.dto.product.LocationAndItemsCountDTO;

import com.example.voebb.model.entity.ProductItem;
import com.example.voebb.repository.ItemLocationRepo;
import com.example.voebb.repository.ProductItemRepo;
import com.example.voebb.service.ProductItemService;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductItemServiceImpl implements ProductItemService {
    private final ProductItemRepo productItemRepo;
    private final ItemLocationRepo itemLocationRepo;

    public ProductItemServiceImpl(ProductItemRepo productItemRepo, ItemLocationRepo itemLocationRepo) {
        this.productItemRepo = productItemRepo;
        this.itemLocationRepo = itemLocationRepo;
    }

    @Override
    public List<ProductItem> getAllByProductId(Long productId) {
        return productItemRepo.findAllByProductId(productId);
    }

    @Override
    public List<LocationAndItemsCountDTO> getLocationsForAvailableItemsByProductId(Long productId) {
        return itemLocationRepo.getLocationAndItemsCount(productId);
    }

    @Override
    public List<LocationAndItemStatusDTO> getAllLocationsForProduct(Long productId){
        return itemLocationRepo.getItemLocationsFullInfo(productId);
    }

    @Override
    public Page<ItemAdminDTO> getAllItems(Pageable pageable) {
        return productItemRepo.findAllItemsForAdmin(pageable);
    }

    @Transactional
    @Override
    public void deleteItemById(Long id) {
        ProductItem item = productItemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with ID: " + id));

        String status = item.getStatus().getName().toLowerCase();

        if (status.equals("borrowed") || status.equals("reserved")) {
            throw new IllegalStateException("Cannot delete item that is currently " + status);
        }

        productItemRepo.delete(item);
    }

}
