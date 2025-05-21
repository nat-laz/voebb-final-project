package com.example.voebb.controller.api;

import com.example.voebb.model.dto.creator.CreatorResponseDTO;
import com.example.voebb.service.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/creators")
@RequiredArgsConstructor
public class CreatorRestController {

    private final CreatorService creatorService;

    @GetMapping("/search")
    public List<CreatorResponseDTO> searchByLastName(@RequestParam String lastName) {
        return creatorService.searchByLastName(lastName.trim());
    }
}
