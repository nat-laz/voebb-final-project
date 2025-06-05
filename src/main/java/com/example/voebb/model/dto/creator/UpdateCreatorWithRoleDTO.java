package com.example.voebb.model.dto.creator;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateCreatorWithRoleDTO {
    private Long creatorId;
    private String firstName;
    private String lastName;
    private Long creatorRoleId;
}