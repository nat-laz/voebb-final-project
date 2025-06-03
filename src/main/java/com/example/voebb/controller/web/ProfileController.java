package com.example.voebb.controller.web;

import com.example.voebb.model.dto.ItemActivityDTO;
import com.example.voebb.model.dto.user.UserUpdateDTO;
import com.example.voebb.service.BorrowService;
import com.example.voebb.service.CustomUserService;
import com.example.voebb.service.ReservationService;
import com.example.voebb.service.impl.ReservationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final CustomUserService userService;
    private final ReservationService reservationService;
    private final BorrowService borrowService;

    @GetMapping
    public String getProfilePage(Model model, Principal principal) {
        String userCredentials = principal.getName();

        UserUpdateDTO userUpdateDTO = userService.getUserUpdateDTOByUsername(userCredentials);
        model.addAttribute("userUpdateDTO", userUpdateDTO);

        List<ItemActivityDTO> activityDTOS = userService.getItemActivitiesByUsername(userCredentials);
        model.addAttribute("activityDTOS", activityDTOS);
        return "public/user/profile";
    }

    @PostMapping("/edit")
    public String postEditUser(@ModelAttribute(name = "userDTO")
                               @Valid
                               UserUpdateDTO userUpdateDTO,
                               Principal principal,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        userService.updateUserInfo(userUpdateDTO, principal.getName(), request, response);
        return "redirect:/profile";
    }

    @PostMapping("/cancel-reservation/{id}")
    public String cancelReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservationService.deleteReservation(id);
            redirectAttributes.addFlashAttribute("success", "Reservation deleted successfully.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Reservation not found.");
        }
        return "redirect:/profile#itemActivity";
    }

    @PostMapping("/extend-borrow/{id}")
    public String extendBorrow(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            String message = borrowService.extendBorrow(id);
            redirectAttributes.addFlashAttribute("success", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/profile#itemActivity";
    }

}
