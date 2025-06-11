package com.example.voebb.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFilters {
    private Long userId;
    private String email;
    private String name;
    private Boolean isEnabled;
}
