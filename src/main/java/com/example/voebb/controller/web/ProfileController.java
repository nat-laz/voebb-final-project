package com.example.voebb.controller.web;

import com.example.voebb.model.dto.user.UserUpdateDTO;
import com.example.voebb.service.impl.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final CustomUserDetailsService userDetailsService;

    @GetMapping
    public String getProfilePage(Model model, Principal principal) {
        model.addAttribute("userUpdateDTO", userDetailsService.getUserDTOByUsername(principal.getName()));
        return "public/user/profile";
    }

    @PostMapping("/edit")
    public String postEditUser(@ModelAttribute(name = "userDTO")
                               @Valid
                               UserUpdateDTO userUpdateDTO,
                               Principal principal,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        userDetailsService.updateUserInfo(userUpdateDTO, principal.getName(), request, response);
        return "redirect:/profile";
    }
}
