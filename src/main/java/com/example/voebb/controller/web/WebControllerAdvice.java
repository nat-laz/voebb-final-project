package com.example.voebb.controller.web;

import com.example.voebb.model.dto.library.LibraryDTO;
import com.example.voebb.model.dto.product.ProductFilters;
import com.example.voebb.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class WebControllerAdvice {

    private final LibraryService libraryService;

    @ModelAttribute("productFilters")
    public ProductFilters productFilters() {
        return new ProductFilters();
    }

    @ModelAttribute("libraries")
    public List<LibraryDTO> libraries() {
        return libraryService.getAllLibraries();
    }

}
