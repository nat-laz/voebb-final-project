package com.example.voebb.controller.web.admin_panel;

import com.example.voebb.model.dto.product.ProductFormDTO;
import com.example.voebb.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductControllerAdmin {

    private final ProductService productService;


    @GetMapping
    public String page() {
        return "admin/products/page";
    }

    @PostMapping
    public String create(@ModelAttribute ProductFormDTO form,
                         RedirectAttributes ra) {

        productService.createProduct(form.mapToRequestDTO());
        ra.addFlashAttribute("success",
                "Product “%s” created successfully".formatted(form.getTitle()));

        return "redirect:/admin/products";
    }

}
