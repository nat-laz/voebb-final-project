package com.example.voebb.controller.web;

import com.example.voebb.model.dto.user.UserRegistrationDTO;
import com.example.voebb.service.impl.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private final CustomUserDetailsService customUserDetailsService;

    public LoginController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "user/register-page";
    }

    @PostMapping("/register")
    public String postRegisterPage(@ModelAttribute UserRegistrationDTO userRegistrationDTO) {
        customUserDetailsService.registerUser(userRegistrationDTO);
        return "redirect:/";
    }
}
