package com.example.voebb.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    // TODO: decide on UI: popup or re-direct to error page
    @ExceptionHandler(CreatorNotFoundException.class)
    public String handleCreatorNotFound(CreatorNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(RuntimeException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/admin/reservations";
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public String handleItemNotFoundException(RuntimeException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/admin/reservations";
    }

    @ExceptionHandler(ItemUnavailableException.class)
    public String handleItemUnavailableException(RuntimeException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/admin/reservations";
    }

    @ExceptionHandler(UserBorrowLimitExceededException.class)
    public String handleUserBorrowLimitExceededException(RuntimeException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/admin/reservations";
    }


    @ExceptionHandler(ItemStatusNotFoundException.class)
    public String handleItemStatusNotFoundException(RuntimeException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/admin/reservations";
    }
}
