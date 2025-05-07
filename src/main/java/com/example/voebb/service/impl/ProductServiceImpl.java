package com.example.voebb.service.impl;

import com.example.voebb.model.dto.creator.CreatorRequestDTO;
import com.example.voebb.model.dto.product.ProductInfoDTO;
import com.example.voebb.model.dto.product.SearchResultProductDTO;
import com.example.voebb.model.entity.Product;
import com.example.voebb.repository.ProductRepo;
import com.example.voebb.service.CreatorProductRelationService;
import com.example.voebb.service.ProductItemService;
import com.example.voebb.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CreatorProductRelationService creatorService;
    private final ProductItemService productItemService;

    public ProductServiceImpl(ProductRepo productRepo, CreatorProductRelationService creatorService, ProductItemService productItemService) {
        this.productRepo = productRepo;
        this.creatorService = creatorService;
        this.productItemService = productItemService;
    }


    @Override
    public Page<SearchResultProductDTO> getAllByTitle(String title, Pageable pageable) {
        Page<Product> page = productRepo.findAllByTitleContainsIgnoreCase(title, pageable);

        return page.map(product -> {
            CreatorRequestDTO mainCreator = creatorService.getCreatorsByProductId(product.getId())
                    .stream()
                    .filter(creator -> creator.roleId() == 1)
                    .findFirst()
                    .map(creatorWithRoleDTO -> new CreatorRequestDTO(
                            creatorWithRoleDTO.firstName(),
                            creatorWithRoleDTO.lastName()
                    ))
                    .orElse(null);

            return new SearchResultProductDTO(
                    product.getId(),
                    product.getType().getName(),
                    product.getTitle(),
                    product.getReleaseYear(),
                    product.getPhoto(),
                    product.getDescription(),
                    product.getProductLinkToEmedia(),
                    mainCreator,
                    productItemService.getLocationsForAvailableItemsByProductId(product.getId()));
        });
    }

    @Override
    public ProductInfoDTO findById(Long id) {
        Product product = productRepo.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return new ProductInfoDTO(
                product.getId(),
                product.getType().getName(),
                product.getTitle(),
                product.getReleaseYear(),
                product.getPhoto(),
                product.getDescription(),
                product.getProductLinkToEmedia()
        );
    }


}
