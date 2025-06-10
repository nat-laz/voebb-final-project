package com.example.voebb.model.dto.user;

public record GetCustomUserDTO(
        Long id,
        String fullName,
        String email,
        String phoneNumber,
        Boolean isEnabled,
        Integer borrowedProductsCount
) {}