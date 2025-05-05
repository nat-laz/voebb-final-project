package com.example.voebb.service.impl;

import com.example.voebb.model.dto.product.ProductRequestDTO;
import com.example.voebb.model.dto.product.ProductResponseDTO;
import com.example.voebb.model.entity.*;
import com.example.voebb.repository.BookDetailsRepo;
import com.example.voebb.repository.ProductRepo;
import com.example.voebb.repository.ProductTypeRepo;
import com.example.voebb.service.*;
import jakarta.persistence.EntityNotFoundException;
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
    private final CountryService countryService;
    private final LanguageService languageService;


    @Autowired
    public ProductServiceImpl(
            ProductRepo productRepo,
            BookDetailsService bookDetailsService,
            CreatorService creatorService,
            ProductTypeService productTypeService,
            CountryService countryService,
            LanguageService languageService) {
        this.productRepo = productRepo;
        this.bookDetailsService = bookDetailsService;
        this.creatorService = creatorService;
        this.productTypeService = productTypeService;
        this.countryService = countryService;
        this.languageService = languageService;
    }

    @Override
    public Page<Product> getAllByTitle(String title, Pageable pageable) {
        return productRepo.findAllByTitleContainsIgnoreCase(title, pageable);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        // 1. Link with existing media_type
        ProductType productType = productTypeService.findByName(requestDTO.productType());

        // 2. Save to Product table
        Product product = new Product();
        product.setTitle(requestDTO.title());
        product.setReleaseYear(requestDTO.releaseYear());
        product.setPhoto(requestDTO.photo());
        product.setDescription(requestDTO.description());
        product.setProductLinkToEmedia(requestDTO.productLinkToEmedia());
        product.setType(productType);

        Product savedProduct = productRepo.save(product);

        // 2. If 'book' || 'e-book' add book details
        if (requestDTO.bookDetails() != null) {
            BookDetails details = new BookDetails();
            details.setIsbn(requestDTO.bookDetails().isbn());
            details.setEdition(requestDTO.bookDetails().edition());
            details.setPages(requestDTO.bookDetails().pages());
            details.setProduct(savedProduct); // associate with product

            bookDetailsService.saveDetails(details); // delegate to service
        }

        // 3. Link creator with the product
        if (requestDTO.creators() != null && !requestDTO.creators().isEmpty()) {
            creatorService.assignCreatorsToProduct(requestDTO.creators(), savedProduct);
        }

        // 4. Link with existing OR create language and country
        Language language = languageService.findOrCreate(requestDTO.language());
        Country country = countryService.findOrCreate(requestDTO.country());

        return new ProductResponseDTO(savedProduct.getId(), savedProduct.getTitle(), productType.getName());

    }

}
