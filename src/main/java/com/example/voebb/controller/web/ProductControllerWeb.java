package com.example.voebb.controller.web;

import com.example.voebb.model.Product;
import com.example.voebb.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductControllerWeb {
    private final ProductService productService;

    public ProductControllerWeb(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public String getSearchPage(Model model) {
        model.addAttribute("title", "");
        return "product/product-search";
    }

    @GetMapping("/search-results")
    public String getSearchResultPage(@PageableDefault(size = 5) Pageable pageable,
                                      @ModelAttribute String title,
                                      Model model) {
        Page<Product> resultProducts = productService.getAllByTitle(title, pageable);
        model.addAttribute("page", resultProducts);
        model.addAttribute("products", resultProducts.getContent());

        return "product/product-list";
    }
}
