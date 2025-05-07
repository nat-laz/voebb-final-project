package com.example.voebb.controller.web;

import com.example.voebb.model.dto.user.UserDTO;
import com.example.voebb.model.entity.CustomUser;
import com.example.voebb.service.CustomUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class CustomUserController {

    private final CustomUserService customUserService;

    public CustomUserController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", customUserService.getAllUsers());
        return "user/user-management";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserDTO(null, "", "", "", true, 0, ""));
        return "user/create-user";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") UserDTO userDto) {
        customUserService.createUser(userDto);
        return "redirect:/users";
    }

    @PostMapping("/toggle/{id}")
    public String toggleUserStatus(@PathVariable Long id) {
        UserDTO user = customUserService.getUserById(id);
        if (user.enabled()) {
            customUserService.disableUser(id);
        } else {
            customUserService.enableUser(id);
        }
        return "redirect:/users";
    }

   /* @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        customUserService.deleteUser(id);
        return "redirect:/users";
    }

    */

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        UserDTO user = customUserService.getUserById(id);
        model.addAttribute("user", user);
        return "user/edit-user";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") UserDTO userDto) {
        customUserService.updateUser(id, userDto);
        return "redirect:/users";
    }
}
