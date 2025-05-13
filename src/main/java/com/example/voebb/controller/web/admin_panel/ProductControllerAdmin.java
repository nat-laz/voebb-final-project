package com.example.voebb.controller.web.admin_panel;

import com.example.voebb.model.dto.product.NewProductDTO;
import com.example.voebb.model.entity.Product;
import com.example.voebb.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductControllerAdmin {

    private final ProductService productService;

    // GET: List all products
    @GetMapping
    public String page(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model,
                       @RequestParam(value = "success", required = false) String success) {

        Page<Product> page = productService.getAllProducts(pageable);
        model.addAttribute("page", page);
        model.addAttribute("products", page.getContent());

        if (success != null && !success.isBlank()) {
            model.addAttribute("success", success);
        }

        return "admin/products/page";
    }

    // GET: Show form to create a new product
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new NewProductDTO()); // For form binding
        return "admin/products/create-product"; // Matches create-product.html
    }

    // POST: Create a new product
    @PostMapping
    public String create(@ModelAttribute("product") NewProductDTO requestDTO,
                         RedirectAttributes ra) {
        productService.createProduct(requestDTO);
        ra.addFlashAttribute("success", "Product created successfully");
        return "redirect:/admin/products";
    }

    // POST: Delete a product by ID
    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id,
                                RedirectAttributes ra) {
        productService.deleteProductById(id);
        ra.addFlashAttribute("success", "Product deleted successfully");
        return "redirect:/admin/products";
    }

    // TODO: add EDIT Product functionality

}
