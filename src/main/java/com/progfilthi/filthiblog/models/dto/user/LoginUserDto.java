package com.progfilthi.filthiblog.models.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserDto(

        @NotBlank(message = "Email is required.")
        @Email
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, message = "Password should be at least 8 characters.")
        String password
) {
}
