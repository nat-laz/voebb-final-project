package com.example.voebb.model.dto.user;

import java.util.List;

public record UserDTO(
        Long id,
        String email,
        String phoneNumber,
        String password,
        String firstName,
        String lastName,
        boolean enabled,
        int borrowedBooksCount,
        List<Long> roleIds
) {
}
