package com.progfilthi.filthiblog.models.dto.post;

import com.progfilthi.filthiblog.models.User;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        PostAuthorDto author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
