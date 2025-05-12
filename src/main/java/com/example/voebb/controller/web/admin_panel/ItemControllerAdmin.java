package com.example.voebb.controller.web.admin_panel;

import com.example.voebb.model.dto.item.ItemAdminDTO;
import com.example.voebb.service.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/items")
@RequiredArgsConstructor
public class ItemControllerAdmin {

    private final ProductItemService productItemService;


    @GetMapping
    public String getAllItems(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model,
                       @RequestParam(value = "success", required = false) String success) {

        Page<ItemAdminDTO> page = productItemService.getAllItems(pageable);
        model.addAttribute("page", page);
        model.addAttribute("items", page.getContent());
        model.addAttribute("pageTitle", "Item Management");

        if (success != null && !success.isBlank()) {
            model.addAttribute("success", success);
        }

        return "admin/item/item-list";
    }

    // TODO: createItem

    // TODO: editItem

    // TODO: deleteItem
}
