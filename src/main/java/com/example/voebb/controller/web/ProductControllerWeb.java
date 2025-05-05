package com.example.voebb.controller.web;

import com.example.voebb.model.dto.product.ProductDTO;
import com.example.voebb.model.entity.Product;
import com.example.voebb.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductControllerWeb {
    private final ProductService productService;

    public ProductControllerWeb(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public String getSearchPage() {
        return "product/product-search";
    }

    @GetMapping("/search-results")
    public String getSearchResultPage(@PageableDefault(size = 5) Pageable pageable,
                                      @RequestParam String title,
                                      Model model) {
        Page<ProductDTO> resultProducts = productService.getAllByTitle(title, pageable);
        model.addAttribute("title", title);
        model.addAttribute("page", resultProducts);
        model.addAttribute("productDTOs", resultProducts.getContent());

        return "product/product-list";
    }
}
