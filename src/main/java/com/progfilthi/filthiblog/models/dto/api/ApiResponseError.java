package com.progfilthi.filthiblog.models.dto.api;

import java.time.LocalDateTime;

public record ApiResponseError(
        String message,
        String path,
        int status,
        LocalDateTime timestamp
) {
}
