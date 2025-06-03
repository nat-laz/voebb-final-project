package com.example.voebb.model.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    @Email(message = "Email should match pattern")
    String email;

    @Pattern(regexp = "(^\\+49[0-9]{3}[0-9]{7,8}$)|(^0[0-9]{3}[0-9]{7,8}$)", message = "Invalid phone number format")
    String phoneNumber;

    String firstName;

    String lastName;

    String newPassword;

    @AssertTrue(message = "New password must be at least 8 characters if provided")
    public boolean isPasswordValid() {
        return newPassword == null  || newPassword.isBlank() || newPassword.length() >= 8;
    }

    @NotNull(message = "Confirmation password must be not null")
    @Size(min = 8, message = "Password must be at least 8 chars long")
    String oldPassword;
}
