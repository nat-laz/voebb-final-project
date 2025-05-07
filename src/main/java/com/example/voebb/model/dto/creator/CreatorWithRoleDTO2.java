package com.example.voebb.model.dto.creator;

public record CreatorWithRoleDTO2(
        Long creatorId,
        String firstName,
        String lastName,
        Long roleId,
        String roleName
) {
}
