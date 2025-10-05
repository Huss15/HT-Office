package com.hassuna.tech.htoffice.base.remote.paylaod;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response payload containing details about the error")
public record ErrorPayload(
    @Schema(description = "Timestamp of the error", example = "2024-06-01T12:34:56.789Z")
        String timestamp,
    @Schema(description = "HTTP status code") int status,
    @Schema(description = "Error type", example = "HTTP error like 5xx or 4xx as text")
        String error,
    @Schema(description = "Detailed error message", example = "Error message") String message,
    @Schema(description = "Request path", example = "/api/v1/some/url") String path) {}
