package com.progfilthi.filthiblog.models.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @NotBlank(message = "Username is required.")
        @Size(min = 3, message = "Username should be at least 3 characters.")
        String username,

        @NotBlank(message = "Email is required.")
        @Email
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, message = "Password should be at least 8 characters.")
        String password
) {
}
