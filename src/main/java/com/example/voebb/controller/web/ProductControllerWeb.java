package com.example.voebb.controller.web;

import com.example.voebb.model.dto.product.ProductInfoDTO;
import com.example.voebb.model.dto.product.CardProductDTO;
import com.example.voebb.service.BookDetailsService;
import com.example.voebb.service.CreatorProductRelationService;
import com.example.voebb.service.ProductItemService;
import com.example.voebb.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductControllerWeb {
    private final ProductService productService;
    private final CreatorProductRelationService creatorProductRelationService;
    private final BookDetailsService bookDetailsService;
    private final ProductItemService productItemService;


    @GetMapping()
    public String getSearchPage() {
        return "user/product/product-search";
    }

    @GetMapping("/search")
    public String getSearchResultPage(@PageableDefault(size = 5) Pageable pageable,
                                      @RequestParam String title,
                                      Model model) {
        Page<CardProductDTO> resultProducts = productService.getProductCardsByTitle(title, pageable);
        model.addAttribute("title", title);
        model.addAttribute("page", resultProducts);
        model.addAttribute("cardProductDTOs", resultProducts.getContent());
        return "user/product/product-list";
    }

    @GetMapping("/products/{id}")
    public String getDetailsPage(@PathVariable Long id,
                                 Model model) {
        ProductInfoDTO productInfoDTO = productService.findById(id);

        model.addAttribute("productInfoDTO", productInfoDTO);
        model.addAttribute("creatorWithRoleDTOs", creatorProductRelationService.getCreatorsByProductId(id));
        model.addAttribute("bookDetailsDTO", bookDetailsService.getDetailsDTOByProductId(id));
        model.addAttribute("locationAndItemStatusDTOs", productItemService.getAllLocationsForProduct(id));
        return "user/product/product-full-details";
    }
}
