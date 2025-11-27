package com.progfilthi.filthiblog.models.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostDto(
        @NotBlank
        @Size(min = 1, max = 100)
        String title,

        @NotBlank
        @Size(min = 1, max = 5000)
        String content
) {
}
