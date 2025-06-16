package com.example.voebb.controller.web;

import com.example.voebb.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/libraries")
public class LibraryControllerWeb {
    private final LibraryService libraryService;

    @GetMapping
    public String getIndexPage(Model model){
        model.addAttribute("librariesInfo", libraryService.getAllLibrariesInfo());
        return "public/library/index";
    }

}