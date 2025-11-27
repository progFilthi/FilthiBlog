package com.progfilthi.filthiblog.models.dto.auth;

import com.progfilthi.filthiblog.models.dto.user.UserResponseDto;

public record AuthResponseDto(
        String token,
        UserResponseDto user
) {
}
