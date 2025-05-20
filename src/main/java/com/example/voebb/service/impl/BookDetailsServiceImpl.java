package com.example.voebb.service.impl;

import com.example.voebb.model.dto.product.BookDetailsDTO;
import com.example.voebb.model.entity.BookDetails;
import com.example.voebb.model.entity.Product;
import com.example.voebb.repository.BookDetailsRepo;
import com.example.voebb.repository.ProductRepo;
import com.example.voebb.service.BookDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookDetailsServiceImpl implements BookDetailsService {

    private final BookDetailsRepo bookDetailsRepo;
    private final ProductRepo productRepo;

    @Override
    public void saveBookDetails(BookDetailsDTO dto, Product product) {
        BookDetails details = new BookDetails();
        details.setIsbn(dto.getIsbn());
        details.setEdition(dto.getEdition());
        details.setPages(dto.getPages());
        details.setProduct(product);
        bookDetailsRepo.save(details);
    }

    @Override
    public BookDetailsDTO getDetailsDTOByProductId(Long productId) {
        Product product = productRepo.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getType() == null) return null;

        String type = product.getType().getName();
        if (!"book".equalsIgnoreCase(type) && !"ebook".equalsIgnoreCase(type)) {
            return null;
        }

        BookDetails details = product.getBookDetails();
        if (details == null) return null;

        return new BookDetailsDTO(
                details.getIsbn(),
                details.getEdition(),
                details.getPages()
        );
    }

    @Override
    public void updateDetails(Product product, BookDetailsDTO newDetails) {
        BookDetails existingDetails = product.getBookDetails();
        existingDetails.setPages(newDetails.getPages());
        existingDetails.setIsbn(newDetails.getIsbn());
        existingDetails.setEdition(newDetails.getEdition());
    }

    @Override
    public void deleteDetails(Long productId) {
        if (!bookDetailsRepo.existsById(productId)) {
            throw new RuntimeException("BookDetails not found for product ID: " + productId);
        }

        bookDetailsRepo.deleteById(productId);
    }


}
