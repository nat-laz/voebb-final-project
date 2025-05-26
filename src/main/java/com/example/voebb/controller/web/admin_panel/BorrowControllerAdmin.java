package com.example.voebb.controller.web.admin_panel;

import com.example.voebb.model.dto.borrow.GetBorrowingsDTO;
import com.example.voebb.service.BorrowService;
import com.example.voebb.service.LibraryService;
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
@RequestMapping("admin/borrowings")
@RequiredArgsConstructor
public class BorrowControllerAdmin {

    private final BorrowService borrowService;
    private final LibraryService libraryService;

    @GetMapping
    public String getAllBorrowings(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long itemId,
            @RequestParam(required = false) Long libraryId,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model
    ) {
        Page<GetBorrowingsDTO> page = borrowService.getFilteredBorrowings(userId, itemId, libraryId, pageable);

        model.addAttribute("page", page);
        model.addAttribute("borrowings", page.getContent());
        model.addAttribute("userId", userId);
        model.addAttribute("itemIdFilter", itemId);
        model.addAttribute("libraryId", libraryId);
        model.addAttribute("libraries", libraryService.getAllLibraries());

        return "admin/borrow/borrow-content";
    }

}
