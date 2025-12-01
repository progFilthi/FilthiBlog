package com.progfilthi.filthiblog.models.dto.comment;

import com.progfilthi.filthiblog.models.dto.post.PostResponseDto;
import com.progfilthi.filthiblog.models.dto.user.UserResponseDto;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        String content,
        UserResponseDto user,
        PostResponseDto post,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
