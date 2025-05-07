package com.example.voebb.service.impl;

import com.example.voebb.model.dto.product.NewBookDetailsDTO;
import com.example.voebb.model.dto.product.NewProductDTO;
import com.example.voebb.model.dto.product.AdminProductDTO;
import com.example.voebb.model.entity.*;
import com.example.voebb.repository.ProductRepo;
import com.example.voebb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final BookDetailsService bookDetailsService;
    private final CreatorService creatorService;
    private final ProductTypeService productTypeService;

    @Autowired
    public ProductServiceImpl(
            ProductRepo productRepo,
            BookDetailsService bookDetailsService,
            CreatorService creatorService,
            ProductTypeService productTypeService) {
        this.productRepo = productRepo;
        this.bookDetailsService = bookDetailsService;
        this.creatorService = creatorService;
        this.productTypeService = productTypeService;

    }

    @Override
    public Page<Product> getAllByTitle(String title, Pageable pageable) {
        return productRepo.findAllByTitleContainsIgnoreCase(title, pageable);
    }

    @Override
    public AdminProductDTO createProduct(NewProductDTO dto) {

        // 1. Link with existing media_type
        // TODO: decide about isDigital should be a field of addProductForm or calculated on the fly if ProductLinkToEmedia exists
        ProductType productType = productTypeService.findOrCreate(
                dto.getProductType().trim());

        // 2. Save to Product table
        Product savedProduct = buildAndSaveProduct(dto, productType);

        // 3. If 'book' || 'e-book' add book details
        String type = dto.getProductType().trim().toLowerCase();
        boolean isBook = type.equals("book") || type.equals("ebook");

        if (isBook && dto.getBookDetails() != null) {
            bookDetailsService.saveBookDetails(dto.getBookDetails(), savedProduct);
        }

        // 4. Link creator with the product
        if (dto.getCreators() == null || dto.getCreators().isEmpty()) {
            throw new IllegalArgumentException("A product must have at least one creator.");
        }
        creatorService.assignCreatorsToProduct(dto.getCreators(), savedProduct);

        // TODO:  Link with existing OR create language and country

        return new AdminProductDTO(
                savedProduct.getId(),
                savedProduct.getTitle(),
                productType.getName());

    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public void deleteProductById(Long productId) {
        if (!productRepo.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(productId);
    }

    private Product buildAndSaveProduct(NewProductDTO dto, ProductType productType) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setReleaseYear(dto.getReleaseYear());
        product.setPhoto(dto.getPhoto());
        product.setDescription(dto.getDescription());
        product.setProductLinkToEmedia(dto.getProductLinkToEmedia());
        product.setType(productType);

        return productRepo.save(product);
    }



}
