package com.progfilthi.filthiblog.models.dto.user;

import com.progfilthi.filthiblog.enums.Roles;

public record UserResponseDto(
        Long id,
        String username,
        String email,
        Roles role
) {
}
