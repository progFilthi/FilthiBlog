package com.progfilthi.filthiblog.models.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentDto(
        @NotBlank(message = "Content is required.")
        @Size(min = 1, max = 500, message = "Comment should not be more than 500 characters.")
        String content,
        Long postId
) {
}
