package com.example.voebb.model.dto.user;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        boolean enabled,
        int borrowedBooksCount,
        String password
) {}
