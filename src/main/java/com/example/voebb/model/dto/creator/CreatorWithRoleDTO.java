package com.example.voebb.model.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatorWithRoleDTO {

    private String firstName;
    private String lastName;
    private String role;
}