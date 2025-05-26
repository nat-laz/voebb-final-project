package com.example.voebb.controller.web;

import com.example.voebb.model.dto.library.LibraryDTO;
import com.example.voebb.model.dto.product.ProductFilters;
import com.example.voebb.model.dto.product.ProductInfoDTO;
import com.example.voebb.model.dto.product.CardProductDTO;
import com.example.voebb.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductControllerWeb {
    private final ProductService productService;
    private final BookDetailsService bookDetailsService;
    private final ProductItemService productItemService;
    private final CreatorService creatorService;
    private final LibraryService libraryService;


    @GetMapping
    public String getIndexPage(@ModelAttribute(name = "libraries") List<LibraryDTO> libraries) {
        return "public/index";
    }

    @GetMapping("/filters")
    public String getExtendedSearchPage(@ModelAttribute(name = "libraries") List<LibraryDTO> libraries,
                                        @ModelAttribute(name = "productFilters") ProductFilters productFilters){
        return "public/product/search-with-filters";
    }

    @GetMapping("/search")
    public String getSearchResultPage(@PageableDefault(size = 10) Pageable pageable,
                                      @ModelAttribute(name = "productFilters") ProductFilters productFilters,
                                      Model model) {
        Page<CardProductDTO> resultProducts = productService.getProductCardsByFilters(productFilters, pageable);
        model.addAttribute("page", resultProducts);
        model.addAttribute("cardProductDTOs", resultProducts.getContent());
        return "public/product/product-list";
    }

    @GetMapping("/products/{id}")
    public String getDetailsPage(@PathVariable Long id,
                                 Model model) {
        ProductInfoDTO productInfoDTO = productService.getProductInfoDTOById(id);

        model.addAttribute("productInfoDTO", productInfoDTO);
        model.addAttribute("bookDetailsDTO", bookDetailsService.getDetailsDTOByProductId(id));
        model.addAttribute("locationAndItemStatusDTOs", productItemService.getAllLocationsForProduct(id));
        return "public/product/product-full-details";
    }
}
