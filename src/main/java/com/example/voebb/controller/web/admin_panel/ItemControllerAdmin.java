package com.example.voebb.controller.web.admin_panel;

import com.example.voebb.model.dto.item.CreateItemDTO;
import com.example.voebb.model.dto.item.ItemAdminDTO;
import com.example.voebb.model.dto.item.ItemFilters;
import com.example.voebb.model.dto.item.UpdateItemDTO;
import com.example.voebb.model.dto.product.ProductInfoDTO;
import com.example.voebb.model.dto.reservation.GetReservationDTO;
import com.example.voebb.service.ItemStatusService;
import com.example.voebb.service.LibraryService;
import com.example.voebb.service.ProductItemService;
import com.example.voebb.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class ItemControllerAdmin {

    private final ProductItemService productItemService;
    private final ProductService productService;


    @GetMapping
    public String getAllItems(Model model,
                              @ModelAttribute("itemFilters") ItemFilters filters,
                              @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(required = false) String searchTitle,
                              @RequestParam(required = false) String action,
                              @RequestParam(value = "success", required = false) String success,
                              @RequestParam(value = "error", required = false) String error) {



        if ("search".equals(action) && searchTitle != null && !searchTitle.isBlank()) {
            Page<ProductInfoDTO> matching = productService.getAllByTitleAdmin(searchTitle, pageable);
            model.addAttribute("matchingProducts", matching);
            model.addAttribute("searchTitle", searchTitle);
            model.addAttribute("openCreateItemModal", true);
        }

        Page<ItemAdminDTO> itemPage = productItemService.getFilteredItems(filters, pageable);
        model.addAttribute("page", itemPage);
        model.addAttribute("items", itemPage.getContent());

        if (success != null) model.addAttribute("success", success);
        if (error != null) model.addAttribute("error", error);

        return "admin/item/item-list";
    }


    @PostMapping("/create")
    public String createItem(@RequestParam Long productId,
                             @RequestParam Long libraryId,
                             @RequestParam String locationNote,
                             RedirectAttributes redirectAttributes) {
        productItemService.createItem(new CreateItemDTO(productId, libraryId, locationNote));
        redirectAttributes.addFlashAttribute("success", "Item created successfully.");
        return "redirect:/admin/items";
    }


    @PostMapping("/edit/{id}")
    public String editItem(@PathVariable Long id , @ModelAttribute UpdateItemDTO updateItemDto,
                           RedirectAttributes redirectAttributes) {
        try {
            productItemService.editItem(id, updateItemDto);
            redirectAttributes.addFlashAttribute("success", "Item updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update item: " + e.getMessage());
        }

        return "redirect:/admin/items";
    }

    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productItemService.deleteItemById(id);
            redirectAttributes.addFlashAttribute("success", "Item deleted successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Item not found.");
        }

        return "redirect:/admin/items";
    }

}
