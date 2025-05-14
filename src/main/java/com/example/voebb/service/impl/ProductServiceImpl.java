package com.example.voebb.service.impl;

import com.example.voebb.model.dto.creator.CreatorRequestDTO;
import com.example.voebb.model.dto.product.ProductInfoDTO;
import com.example.voebb.model.dto.product.SearchResultProductDTO;
import com.example.voebb.model.entity.Product;
import com.example.voebb.repository.CountryRepo;
import com.example.voebb.repository.ProductRepo;
import com.example.voebb.service.CreatorProductRelationService;
import com.example.voebb.service.ProductItemService;
import com.example.voebb.service.ProductService;
import com.example.voebb.model.dto.product.NewProductDTO;
import com.example.voebb.model.dto.product.AdminProductDTO;
import com.example.voebb.model.entity.*;
import com.example.voebb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CreatorProductRelationService creatorProductRelationService;
    private final ProductItemService productItemService;
    private final BookDetailsService bookDetailsService;
    private final CreatorService creatorService;
    private final ProductTypeService productTypeService;
    private final CountryRepo countryRepo;


    @Autowired
    public ProductServiceImpl(
            ProductRepo productRepo,
            BookDetailsService bookDetailsService,
            CreatorProductRelationService creatorProductRelationService,
            CreatorService creatorService,
            ProductTypeService productTypeService,
            CountryRepo countryRepo,

            ProductItemService productItemService) {
        this.productRepo = productRepo;
        this.bookDetailsService = bookDetailsService;
        this.creatorService = creatorService;
        this.productTypeService = productTypeService;
        this.creatorProductRelationService = creatorProductRelationService;
        this.productItemService = productItemService;
        this.countryRepo = countryRepo;
    }

    @Override
    public Page<SearchResultProductDTO> getAllByTitle(String title, Pageable pageable) {
        Page<Product> page = productRepo.findAllByTitleContainsIgnoreCase(title, pageable);

        return page.map(product -> {
            CreatorRequestDTO mainCreator = creatorProductRelationService.getCreatorsByProductId(product.getId())
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
                    product.getProductLinkToEmedia(),
                    mainCreator,
                    productItemService.getLocationsForAvailableItemsByProductId(product.getId()));
        });
    }

    @Override

    public ProductInfoDTO findById(Long id) {
        Product product = productRepo.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Set<String> countryNames = product.getCountries()
                .stream()
                .map(Country::getName)
                .collect(Collectors.toSet());


        return new ProductInfoDTO(
                product.getId(),
                product.getType().getName(),
                product.getTitle(),
                product.getReleaseYear(),
                product.getPhoto(),
                product.getDescription(),
                product.getProductLinkToEmedia(),
                countryNames
        );
    }




    @Override
    public AdminProductDTO createProduct(NewProductDTO dto, List<Long> selectedCountryIds) {
        // 1. Link with existing media_type
        ProductType productType = productTypeService.findOrCreate(dto.getProductType().trim());

        // 2. Save to Product table
        Product savedProduct = buildAndSaveProduct(dto, productType, selectedCountryIds);

        // 3. If 'book' or 'e-book', add book details
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

    @Override
    public Product getProductById(Long id) {
        return productRepo.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct, List<Long> selectedCountryIds) {
        Product existingProduct = getProductById(id);

        // Update fields
        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setReleaseYear(updatedProduct.getReleaseYear());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPhoto(updatedProduct.getPhoto());
        existingProduct.setProductLinkToEmedia(updatedProduct.getProductLinkToEmedia());

        // Handle product type if you allow changing it
        if (updatedProduct.getType() != null) {
            existingProduct.setType(updatedProduct.getType());
        }

        // Update countries
        if (selectedCountryIds != null) {
            Set<Country> countries = new HashSet<>(countryRepo.findAllById(selectedCountryIds));
            existingProduct.setCountries(countries);
        }

        return productRepo.save(existingProduct);
    }

    @Override
    public void saveProduct(Product existingProduct) {
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (existingProduct.getId() == null) {
            throw new IllegalArgumentException("Product must have an ID");
        }
        if (!productRepo.existsById(existingProduct.getId())) {
            throw new IllegalArgumentException("Product with ID " + existingProduct.getId() + " does not exist");
        }
        productRepo.save(existingProduct);
    }

    private Product buildAndSaveProduct(NewProductDTO dto, ProductType productType, List<Long> selectedCountryIds) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setReleaseYear(dto.getReleaseYear());
        product.setPhoto(dto.getPhoto());
        product.setDescription(dto.getDescription());
        product.setProductLinkToEmedia(dto.getProductLinkToEmedia());
        product.setType(productType);

        if (selectedCountryIds != null && !selectedCountryIds.isEmpty()) {
            List<Country> countries = countryRepo.findAllById(selectedCountryIds);
            product.setCountries(new HashSet<>(countries));
        }

        return productRepo.save(product);
    }

}
