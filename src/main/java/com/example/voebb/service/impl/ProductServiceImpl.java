package com.example.voebb.service.impl;

import com.example.voebb.model.dto.product.*;
import com.example.voebb.model.entity.Country;
import com.example.voebb.model.entity.Product;
import com.example.voebb.model.entity.ProductType;
import com.example.voebb.model.mapper.ProductMapper;
import com.example.voebb.repository.CountryRepo;
import com.example.voebb.repository.ProductRepo;
import com.example.voebb.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CreatorProductRelationService creatorProductRelationService;
    private final ProductItemService productItemService;
    private final BookDetailsService bookDetailsService;
    private final CreatorService creatorService;
    private final ProductTypeService productTypeService;
    private final CountryRepo countryRepo;


    @Override
    public Page<CardProductDTO> getProductCardsByTitle(String title, Pageable pageable) {
        Page<Product> page = productRepo.findAllByTitleContainsIgnoreCase(title, pageable);

        return page.map(product -> new CardProductDTO(
                product.getId(),
                product.getType().getName(),
                product.getTitle(),
                product.getReleaseYear(),
                product.getPhoto(),
                product.getProductLinkToEmedia(),
                creatorProductRelationService.getMainCreators(product.getId(), product.getType().getMainCreatorRoleId())
                        .stream()
                        .map(s -> s.firstName() + " " + s.lastName())
                        .collect(Collectors.joining(", ")),
                productItemService.getLocationsForAvailableItemsByProductId(product.getId())));
    }

    @Override
    public Page<ProductInfoDTO> getAllByTitleAdmin(String title, Pageable pageable) {
        Page<Product> page = productRepo.findAllByTitleContainsIgnoreCase(title, pageable);
        return page.map(ProductMapper::toProductInfoDTO);
    }

    @Override
    public ProductInfoDTO findById(Long id) {
        Product product = productRepo.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.toProductInfoDTO(product);
    }

    @Override
    public void createProduct(CreateProductDTO dto) {

        // 1. Link with existing media_type
        ProductType productType = productTypeService.findOrCreate(dto.getProductType().trim());

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
    public UpdateProductDTO getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        BookDetailsDTO bookDetailsDTO = new BookDetailsDTO(
                product.getBookDetails().getIsbn(),
                product.getBookDetails().getEdition(),
                product.getBookDetails().getPages()
        );

        // TODO: Make mapper
        return new UpdateProductDTO(
                product.getId(),
                product.getTitle(),
                product.getReleaseYear(),
                product.getPhoto(),
                product.getDescription(),
                product.getProductLinkToEmedia(),
                bookDetailsDTO,
                product.getCountries().stream().map(
                        Country::getId
                ).toList()
        );
    }

    @Override
    @Transactional
    public UpdateProductDTO updateProduct(Long productId, UpdateProductDTO updateProductDTO) {
        Product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Country> countries = countryRepo.findAllById(updateProductDTO.countryIds());

        if (existingProduct.isBook()) {
            bookDetailsService.updateDetails(existingProduct, updateProductDTO.bookDetails());
        }

        // TODO: make mapper
        existingProduct.setTitle(updateProductDTO.title());
        existingProduct.setReleaseYear(updateProductDTO.releaseYear());
        existingProduct.setDescription(updateProductDTO.description());
        existingProduct.setPhoto(updateProductDTO.photo());
        existingProduct.setProductLinkToEmedia(updateProductDTO.productLinkToEmedia());
        existingProduct.setCountries(countries);
        productRepo.save(existingProduct);
        return updateProductDTO;
    }

    @Override
    @Transactional
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

    // TODO: extract mapper method from here
    private Product buildAndSaveProduct(CreateProductDTO dto, ProductType productType) {
        List<Country> countries = countryRepo.findAllById(dto.getCountryIds());

        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setReleaseYear(dto.getReleaseYear());
        product.setPhoto(dto.getPhoto());
        product.setDescription(dto.getDescription());
        product.setProductLinkToEmedia(dto.getProductLinkToEmedia());
        product.setType(productType);
        product.setCountries(countries);
        return productRepo.save(product);
    }

}
