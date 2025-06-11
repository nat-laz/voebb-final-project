package com.example.voebb.controller.web.admin_panel;

import com.example.voebb.model.dto.user.GetCustomUserDTO;
import com.example.voebb.model.dto.user.UserDTO;
import com.example.voebb.model.dto.user.UserFilters;
import com.example.voebb.repository.CustomUserRoleRepo;
import com.example.voebb.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserControllerAdmin {

    private final CustomUserService customUserService;
    private final CustomUserRoleRepo customUserRoleRepo;


    @GetMapping
    public String getAllUsers(Model model,
                              @ModelAttribute("userFilters") UserFilters filters,
                              @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(value = "success", required = false) String success) {

        Page<GetCustomUserDTO> page = customUserService.getFilteredUsers(filters, pageable);
        model.addAttribute("page", page);
        model.addAttribute("users", page.getContent());

        if (success != null && !success.isBlank()) {
            model.addAttribute("success", success);
        }

        return "admin/user/user-management";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("userDTO", new UserDTO(null, "", "", "", "", "", true, 0, List.of()));
        model.addAttribute("allRoles", customUserRoleRepo.findAll());
        return "admin/user/create-user";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") UserDTO userDto) {
        customUserService.createUser(userDto);
        return "redirect:/admin/users";
    }

    @PostMapping("/toggle/{id}")
    public String toggleUserStatus(@PathVariable Long id) {
        UserDTO user = customUserService.getUserById(id);
        if (user.enabled()) {
            customUserService.disableUser(id);
        } else {
            customUserService.enableUser(id);
        }
        return "redirect:/admin/users";
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        UserDTO user = customUserService.getUserById(id);
        model.addAttribute("userDTO", user);
        model.addAttribute("allRoles", customUserRoleRepo.findAll());
        return "admin/user/edit-user";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("userDTO") UserDTO userDto) {
        customUserService.updateUser(id, userDto);
        return "redirect:/admin/users";
    }
}
