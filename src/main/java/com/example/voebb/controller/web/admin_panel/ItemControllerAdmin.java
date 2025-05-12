package com.example.voebb.controller.web.admin_panel;

import com.example.voebb.model.dto.item.ItemAdminDTO;
import com.example.voebb.model.dto.item.UpdateItemDTO;
import com.example.voebb.service.ItemStatusService;
import com.example.voebb.service.LibraryService;
import com.example.voebb.service.ProductItemService;
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
    private final ItemStatusService itemStatusService;
    private final LibraryService libraryService;


    @GetMapping
    public String getAllItems(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              Model model,
                              @RequestParam(value = "success", required = false) String success,
                              @RequestParam(value = "error", required = false) String error) {

        Page<ItemAdminDTO> page = productItemService.getAllItems(pageable);

        model.addAttribute("page", page);
        model.addAttribute("items", page.getContent());
        model.addAttribute("pageTitle", "Item Management");

        // Statuses and Libraries for modals
        model.addAttribute("statuses", itemStatusService.filterEditableStatusesForItemManagement());
        model.addAttribute("libraries", libraryService.getAllLibraries());

        if (success != null) model.addAttribute("success", success);
        if (error != null) model.addAttribute("error", error);

        return "admin/item/item-list";
    }

    // TODO: createItem

    @PostMapping("/{id}/edit")
    public String editItem(@PathVariable Long id,
                           @ModelAttribute UpdateItemDTO dto,
                           RedirectAttributes redirectAttributes) {
        try {
            productItemService.editItem(dto);
            redirectAttributes.addFlashAttribute("success", "Item updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update item: " + e.getMessage());
        }

        return "redirect:/admin/items";
    }

    @PostMapping("/{id}/delete")
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
