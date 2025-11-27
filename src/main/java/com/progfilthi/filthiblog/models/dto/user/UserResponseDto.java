package com.progfilthi.filthiblog.models.dto.user;

public record UserResponseDto(
        Long id,
        String username,
        String email
) {
}
