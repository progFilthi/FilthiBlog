package com.progfilthi.filthiblog.models.dto.post;

import com.progfilthi.filthiblog.enums.Roles;

public record PostAuthorDto(
        Long id,
        String username,
        String email,
        Roles role
) {
}
