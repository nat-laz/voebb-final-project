package com.example.voebb.service.impl;

import com.example.voebb.model.dto.item.ItemAdminDTO;
import com.example.voebb.model.dto.item.UpdateItemDTO;
import com.example.voebb.model.dto.product.LocationAndItemStatusDTO;
import com.example.voebb.model.dto.product.LocationAndItemsCountDTO;

import com.example.voebb.model.entity.ItemLocation;
import com.example.voebb.model.entity.ItemStatus;
import com.example.voebb.model.entity.Library;
import com.example.voebb.model.entity.ProductItem;
import com.example.voebb.repository.ItemLocationRepo;
import com.example.voebb.repository.ItemStatusRepo;
import com.example.voebb.repository.LibraryRepo;
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
    private final ItemStatusRepo itemStatusRepo;
    private final LibraryRepo libraryRepo;

    public ProductItemServiceImpl(
            ProductItemRepo productItemRepo,
            ItemLocationRepo itemLocationRepo,
            ItemStatusRepo itemStatusRepo,
            LibraryRepo libraryRepo) {
        this.productItemRepo = productItemRepo;
        this.itemLocationRepo = itemLocationRepo;
        this.itemStatusRepo = itemStatusRepo;
        this.libraryRepo = libraryRepo;
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
    public List<LocationAndItemStatusDTO> getAllLocationsForProduct(Long productId) {
        return itemLocationRepo.getItemLocationsFullInfo(productId);
    }

    @Override
    public Page<ItemAdminDTO> getAllItems(Pageable pageable) {
        return productItemRepo.findAllItemsForAdmin(pageable);
    }

    @Override
    @Transactional
    public void editItem(UpdateItemDTO dto) {
        ProductItem item = productItemRepo.findById(dto.itemId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found with ID: " + dto.itemId()));

        ItemStatus newStatus = itemStatusRepo.findById(dto.statusId())
                .orElseThrow(() -> new EntityNotFoundException("Item status not found with ID: " + dto.statusId()));

        Library newLibrary = libraryRepo.findById(dto.libraryId())
                .orElseThrow(() -> new EntityNotFoundException("Library not found with ID: " + dto.libraryId()));

        item.setStatus(newStatus);

        if (item.getLocation() == null) {
            ItemLocation newLocation = new ItemLocation();
            newLocation.setItem(item);
            item.setLocation(newLocation);
        }

        item.getLocation().setLibrary(newLibrary);
        item.getLocation().setNote(dto.locationNote());

        productItemRepo.save(item);
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
