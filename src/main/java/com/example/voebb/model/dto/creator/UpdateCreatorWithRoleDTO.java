package com.example.voebb.model.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreatorWithRoleDTO {
    private Long creatorId;
    private String firstName;
    private String lastName;
    private Long creatorRoleId;
}