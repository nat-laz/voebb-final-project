package com.example.voebb.service.impl;

import com.example.voebb.model.dto.creator.CreatorRequestDTO;
import com.example.voebb.model.dto.product.ProductDTO;
import com.example.voebb.model.entity.Product;
import com.example.voebb.repository.ProductRepo;
import com.example.voebb.service.CreatorProductRelationService;
import com.example.voebb.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CreatorProductRelationService creatorService;

    public ProductServiceImpl(ProductRepo productRepo, CreatorProductRelationService creatorService) {
        this.productRepo = productRepo;
        this.creatorService = creatorService;
    }


    @Override
    public Page<ProductDTO> getAllByTitle(String title, Pageable pageable) {
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

            return new ProductDTO(
                    product.getId(),
                    product.getType().getName(),
                    product.getTitle(),
                    product.getReleaseYear(),
                    product.getPhoto(),
                    product.getDescription(),
                    product.getProductLinkToEmedia(),
                    mainCreator);
        });
    }

}
